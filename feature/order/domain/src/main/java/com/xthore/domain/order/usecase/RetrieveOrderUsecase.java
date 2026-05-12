package com.xthore.domain.order.usecase;

import com.xthore.common.usecase.MonoWithArgsUsecase;
import com.xthore.domain.order.model.Order;
import com.xthore.domain.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
public class RetrieveOrderUsecase implements MonoWithArgsUsecase<UUID, Order> {
    private final OrderRepository orderRepository;

    public RetrieveOrderUsecase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Mono<Order> execute(UUID id) {
        return orderRepository.findById(id);
    }
}
