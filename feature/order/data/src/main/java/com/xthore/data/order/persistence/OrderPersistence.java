package com.xthore.data.order.persistence;

import com.xthore.common.model.Paging;
import com.xthore.domain.order.model.Order;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface OrderPersistence {
    Mono<Order> save(Order order);

    Mono<Order> findById(UUID id);

    Paging.Response<Order> findAll(String category, Paging.Page page);

    Mono<Long> count(String category);
}
