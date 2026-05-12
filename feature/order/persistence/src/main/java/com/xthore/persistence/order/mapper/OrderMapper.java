package com.xthore.persistence.order.mapper;

import com.xthore.domain.order.model.Article;
import com.xthore.domain.order.model.Order;
import com.xthore.domain.order.model.PaymentMethod;
import com.xthore.persistence.order.entity.ArticleEntity;
import com.xthore.persistence.order.entity.CustomerEntity;
import com.xthore.persistence.order.entity.OrderEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderEntity mapFromDomain(Order order) {
        return new OrderEntity(
                order.id(),
                order.state().name(),
                order.category(),
                UUID.fromString(order.customer()),
                order.site(),
                order.createdAt(),
                order.updatedAt()
        );
    }

    public static Order mapToDomain(OrderEntity orderEntity, List<ArticleEntity> articles, CustomerEntity customer) {
        List<Article> items = articles.stream()
                .map(a -> new Article(a.offering(), a.quantity()))
                .collect(Collectors.toList());
        PaymentMethod paymentMethod = new PaymentMethod(
                PaymentMethod.Type.valueOf(customer.paymentMethod()),
                customer.iban()
        );
        return new Order(
                orderEntity.uuid(),
                Order.State.valueOf(orderEntity.state()),
                orderEntity.category(),
                orderEntity.customer().toString(),
                orderEntity.siteId(),
                items,
                paymentMethod,
                orderEntity.createdAt(),
                orderEntity.updatedAt()
        );
    }
}
