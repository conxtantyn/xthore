package com.xthore.data.order.repository;

import com.xthore.common.model.Paging;
import com.xthore.data.order.persistence.OrderPersistence;
import com.xthore.domain.order.model.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class OrderRepositoryDelegateTest {

    @Mock
    private OrderPersistence persistence;

    @InjectMocks
    private OrderRepositoryDelegate repository;

    @Test
    @SuppressWarnings("unchecked")
    void shouldSaveOrder() {
        Order order = mock(Order.class);
        when(persistence.save(order)).thenReturn(Mono.just(order));
        StepVerifier.create(repository.save(order))
                .expectNext(order)
                .verifyComplete();
        verify(persistence).save(order);
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldFindById() {
        UUID id = UUID.randomUUID();
        Order order = mock(Order.class);
        when(persistence.findById(id)).thenReturn(Mono.just(order));
        StepVerifier.create(repository.findById(id))
                .expectNext(order)
                .verifyComplete();
        verify(persistence).findById(id);
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldFindAll() {
        String category = "cat";
        Paging.Page page = new Paging.Page(0, 10);
        Paging.Response<Order> response = mock(Paging.Response.class);
        when(persistence.findAll(category, page)).thenReturn(response);
        Paging.Response<Order> actual = repository.findAll(category, page);
        assertThat(actual).isEqualTo(response);
        verify(persistence).findAll(category, page);
    }
}
