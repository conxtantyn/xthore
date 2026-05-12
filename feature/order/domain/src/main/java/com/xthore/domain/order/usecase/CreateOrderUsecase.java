package com.xthore.domain.order.usecase;

import com.xthore.common.usecase.MonoWithArgsUsecase;
import com.xthore.domain.order.interactor.SessionInteractor;
import com.xthore.domain.order.model.*;
import com.xthore.domain.order.repository.OrderRepository;
import com.xthore.domain.order.exception.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
public class CreateOrderUsecase implements MonoWithArgsUsecase<CreateOrderUsecase.Args, Order> {
    private final OrderRepository orderRepository;
    private final SessionInteractor interactor;

    public CreateOrderUsecase(OrderRepository orderRepository, SessionInteractor interactor) {
        this.orderRepository = orderRepository;
        this.interactor = interactor;
    }

    @Override
    public Mono<Order> execute(Args args) {
        return interactor.check(args.session(), args.request())
            .flatMap(record -> orderRepository.findById(record.order()))
            .switchIfEmpty(Mono.defer(() -> createNewOrder(args)));
    }

    private Mono<Order> createNewOrder(Args args) {
        if (args.articles() == null || args.articles().isEmpty()) {
            return Mono.error(new ValidationException());
        }
        Order order = new Order(
            UUID.randomUUID(),
            Order.State.DRAFT,
            args.category(),
            args.customer(),
            args.site(),
            args.articles(),
            args.paymentMethod(),
            System.currentTimeMillis(),
            System.currentTimeMillis()
        );
        return orderRepository.save(order)
            .flatMap(savedOrder ->
                    interactor.create(args.session(), args.request(), savedOrder.id())
                    .thenReturn(savedOrder)
            );
    }

    public record Args(
            String category,
            String customer,
            String site,
            List<Article> articles,
            PaymentMethod paymentMethod,
            String session,
            String request
    ) {}
}
