package com.xthore.data.catalog.repository;

import com.xthore.data.catalog.persistence.OfferingPersistence;
import com.xthore.domain.catalog.model.Offering;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferingRepositoryDelegateTest {

    @Mock
    private OfferingPersistence persistence;

    private OfferingRepositoryDelegate delegate;

    @BeforeEach
    void setUp() {
        delegate = new OfferingRepositoryDelegate(persistence);
    }

    @Test
    void shouldFindAllOfferings() {
        Offering offering = new Offering("Test", "Desc", System.currentTimeMillis());
        when(persistence.findAll()).thenReturn(Flux.just(offering));
        StepVerifier.create(delegate.findAll())
                .expectNext(offering)
                .verifyComplete();
        verify(persistence).findAll();
    }

    @Test
    void shouldFindOfferingByProduct() {
        String product = UUID.randomUUID().toString();
        Offering offering = new Offering("Test", "Desc", System.currentTimeMillis());
        when(persistence.findByProduct(product)).thenReturn(Mono.just(offering));
        StepVerifier.create(delegate.findByProduct(product))
                .expectNext(offering)
                .verifyComplete();
        verify(persistence).findByProduct(product);
    }

    @Test
    void shouldCheckAvailability() {
        String product = UUID.randomUUID().toString();
        when(persistence.isAvailable(product)).thenReturn(Mono.just(true));
        StepVerifier.create(delegate.isAvailable(product))
                .expectNext(true)
                .verifyComplete();
        verify(persistence).isAvailable(product);
    }
}
