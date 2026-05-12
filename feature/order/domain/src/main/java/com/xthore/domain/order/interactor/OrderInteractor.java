package com.xthore.domain.order.interactor;

import com.xthore.domain.order.model.Order;
import com.xthore.domain.order.model.OrderDetail;

import reactor.core.publisher.Mono;

public interface OrderInteractor {
    boolean isValid(Order.State from, Order.State to);

    Mono<Order> update(OrderDetail detail);
}
