package com.xthore.data.order.interactor;

import com.xthore.domain.order.exception.*;
import com.xthore.domain.order.interactor.OrderInteractor;
import com.xthore.domain.order.model.Order;
import com.xthore.domain.order.model.OrderDetail;
import com.xthore.domain.order.repository.OrderRepository;

import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import reactor.core.publisher.Mono;

@Service
public class OrderInteractorDelegate implements OrderInteractor {
    private static final Map<Order.State, Set<Order.State>> ALLOWED_TRANSITIONS = new EnumMap<>(Order.State.class);

    static {
        ALLOWED_TRANSITIONS.put(Order.State.DRAFT, EnumSet.of(Order.State.PREVIEW));
        ALLOWED_TRANSITIONS.put(Order.State.PREVIEW, EnumSet.of(Order.State.DRAFT, Order.State.SUBMITTED));
        ALLOWED_TRANSITIONS.put(Order.State.SUBMITTED, EnumSet.of(Order.State.CONFIRMED));
        ALLOWED_TRANSITIONS.put(Order.State.CONFIRMED, EnumSet.noneOf(Order.State.class));
    }

    private final OrderRepository repository;

    public OrderInteractorDelegate(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isValid(Order.State from, Order.State to) {
        if (from == to) return true;
        Set<Order.State> allowed = ALLOWED_TRANSITIONS.get(from);
        return allowed != null && allowed.contains(to);
    }

    @Override
    public Mono<Order> update(OrderDetail detail) {
        return repository.findById(detail.order())
                .switchIfEmpty(Mono.error(new OrderNotFoundException()))
                .flatMap(existing -> validateAndMerge(existing, detail))
                .flatMap(repository::save);
    }

    private Mono<Order> validateAndMerge(Order existing, OrderDetail args) {
        if (existing.state() == Order.State.CONFIRMED) {
            return Mono.error(new OrderImmutableException());
        }
        Order.State targetState = args.state() != null ? args.state() : existing.state();
        if (!isValid(existing.state(), targetState)) {
            return Mono.error(new InvalidStateTransitionException());
        }
        if (existing.state() == Order.State.SUBMITTED) {
            if (args.category() != null || args.customer() != null || args.site() != null ||
                    args.articles() != null || args.paymentMethod() != null) {
                return Mono.error(new OrderImmutableException());
            }
        }
        Order updated = new Order(
                existing.id(),
                targetState,
                args.category() != null ? args.category() : existing.category(),
                args.customer() != null ? args.customer() : existing.customer(),
                args.site() != null ? args.site() : existing.site(),
                args.articles() != null ? args.articles() : existing.articles(),
                args.paymentMethod() != null ? args.paymentMethod() : existing.paymentMethod(),
                existing.createdAt(),
                System.currentTimeMillis()
        );
        return Mono.just(updated);
    }
}
