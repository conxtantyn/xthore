package com.xthore.domain.order.usecase;

import com.xthore.common.usecase.MonoWithArgsUsecase;
import com.xthore.domain.order.interactor.OrderInteractor;
import com.xthore.domain.order.model.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateOrderUsecase implements MonoWithArgsUsecase<OrderDetail, Order> {
    private final OrderInteractor interactor;

    public UpdateOrderUsecase(OrderInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public Mono<Order> execute(OrderDetail args) {
        return interactor.update(args);
    }
}
