package com.xthore.data.order.interactor;

import com.xthore.domain.order.exception.OrderImmutableException;
import com.xthore.domain.order.exception.OrderNotFoundException;
import com.xthore.domain.order.model.Order;
import com.xthore.domain.order.model.OrderDetail;
import com.xthore.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class OrderInteractorDelegateTest {

    @Mock
    private OrderRepository repository;

    @InjectMocks
    private OrderInteractorDelegate interactor;

    @Test
    @SuppressWarnings("unchecked")
    void shouldThrowNotFoundWhenOrderDoesNotExist() {
        UUID id = UUID.randomUUID();
        OrderDetail detail = mock(OrderDetail.class);
        when(detail.order()).thenReturn(id);
        when(repository.findById(id)).thenReturn(Mono.empty());
        StepVerifier.create(interactor.update(detail))
                .expectError(OrderNotFoundException.class)
                .verify();
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldThrowImmutableWhenOrderIsConfirmed() {
        UUID id = UUID.randomUUID();
        Order existing = mock(Order.class);
        OrderDetail detail = mock(OrderDetail.class);
        when(detail.order()).thenReturn(id);
        when(repository.findById(id)).thenReturn(Mono.just(existing));
        when(existing.state()).thenReturn(Order.State.CONFIRMED);
        StepVerifier.create(interactor.update(detail))
                .expectError(OrderImmutableException.class)
                .verify();
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldUpdateOrder() {
        UUID id = UUID.randomUUID();
        Order existing = new Order(
                id, Order.State.DRAFT, "old", "cust", "site", List.of(), null, 0L, 0L);
        OrderDetail detail = new OrderDetail(
                id, Order.State.PREVIEW, "new", "cust", "site", List.of(), null);
        when(repository.findById(id)).thenReturn(Mono.just(existing));
        when(repository.save(any(Order.class))).thenAnswer(i -> Mono.just(i.getArgument(0)));
        StepVerifier.create(interactor.update(detail))
                .assertNext(updated -> {
                    assertThat(updated.id()).isEqualTo(id);
                    assertThat(updated.state()).isEqualTo(Order.State.PREVIEW);
                    assertThat(updated.category()).isEqualTo("new");
                })
                .verifyComplete();
    }
}
