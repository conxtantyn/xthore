package com.xthore.data.order.repository;

import com.xthore.domain.order.repository.OrderRepository;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryDelegate implements OrderRepository {
    private final OrderRepository repository;

    public OrderRepositoryDelegate(OrderRepository repository) {
        this.repository = repository;
    }
}
