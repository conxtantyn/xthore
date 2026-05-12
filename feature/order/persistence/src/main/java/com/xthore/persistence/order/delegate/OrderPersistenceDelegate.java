package com.xthore.persistence.order.delegate;

import com.xthore.common.model.Paging;
import com.xthore.data.order.persistence.OrderPersistence;
import com.xthore.domain.order.model.*;
import com.xthore.persistence.order.datasource.*;
import com.xthore.persistence.order.entity.*;
import com.xthore.persistence.order.mapper.ArticleMapper;
import com.xthore.persistence.order.mapper.OrderMapper;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
public class OrderPersistenceDelegate implements OrderPersistence {
    private final OrderDatasource orderDatasource;
    private final ArticleDatasource articleDatasource;
    private final CustomerDatasource customerDatasource;

    public OrderPersistenceDelegate(
        OrderDatasource orderDatasource,
        ArticleDatasource articleDatasource,
        CustomerDatasource customerDatasource
    ) {
        this.orderDatasource = orderDatasource;
        this.articleDatasource = articleDatasource;
        this.customerDatasource = customerDatasource;
    }

    @Override
    public Mono<Order> save(Order order) {
        OrderEntity entity = OrderMapper.mapFromDomain(order);
        return orderDatasource.save(entity)
            .flatMap(savedOrder -> {
                Flux<ArticleEntity> articles = Flux.fromIterable(order.articles())
                    .map(item -> ArticleMapper.mapToDomain(savedOrder.uuid(), item));
                return articleDatasource.saveAll(articles).collectList()
                    .thenReturn(savedOrder);
            }).flatMap(savedOrder -> findById(savedOrder.uuid()));
    }

    @Override
    public Mono<Order> findById(UUID id) {
        return orderDatasource.findById(id)
            .flatMap(orderEntity -> 
                articleDatasource.findByOrderUuid(orderEntity.uuid()).collectList()
                    .flatMap(articles -> 
                        customerDatasource.findById(orderEntity.customer())
                            .map(customer -> OrderMapper
                                    .mapToDomain(orderEntity, articles, customer))
                    )
            );
    }

    @Override
    public Paging.Response<Order> findAll(String category, Paging.Page page) {
        Flux<Order> items = orderDatasource.findAll()
            .filter(o -> category == null || o.category().equals(category))
            .skip(page.index())
            .take(page.size())
            .flatMap(o -> findById(o.uuid()));
        Mono<Paging.Pageable> pageable = count(category)
            .map(total -> new Paging.Pageable(page.index(), page.size(), 0, total));
        return new Paging.Response<>(pageable, items);
    }

    @Override
    public Mono<Long> count(String category) {
        return orderDatasource.findAll()
            .filter(o -> category == null || o.category().equals(category))
            .count();
    }
}
