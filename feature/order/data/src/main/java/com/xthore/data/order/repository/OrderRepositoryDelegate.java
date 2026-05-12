package com.xthore.data.order.repository;

import com.xthore.common.model.Paging;
import com.xthore.data.order.persistence.OrderPersistence;
import com.xthore.domain.order.model.Order;
import com.xthore.domain.order.repository.OrderRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public class OrderRepositoryDelegate implements OrderRepository {
    private final OrderPersistence persistence;

    public OrderRepositoryDelegate(OrderPersistence persistence) {
        this.persistence = persistence;
    }

    @Override
    public Mono<Order> save(Order order) {
        return persistence.save(order);
    }

    @Override
    public Mono<Order> findById(UUID id) {
        return persistence.findById(id);
    }

    @Override
    public Paging.Response<Order> findAll(String category, Paging.Page page) {
        return persistence.findAll(category, page);
    }

    @Override
    public Mono<Long> count(String category) {
        return persistence.count(category);
    }
}
