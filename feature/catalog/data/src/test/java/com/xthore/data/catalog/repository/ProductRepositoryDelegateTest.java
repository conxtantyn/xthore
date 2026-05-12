package com.xthore.data.catalog.repository;

import com.xthore.common.model.Paging;
import com.xthore.data.catalog.persistence.ProductPersistence;
import com.xthore.domain.catalog.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryDelegateTest {

    @Mock
    private ProductPersistence persistence;

    private ProductRepositoryDelegate delegate;

    @BeforeEach
    void setUp() {
        delegate = new ProductRepositoryDelegate(persistence);
    }

    @Test
    void shouldFindProductByUuid() {
        String uuid = UUID.randomUUID().toString();
        Product product = new Product(uuid, "Test", 10.0);
        when(persistence.find(uuid)).thenReturn(Mono.just(product));
        StepVerifier.create(delegate.find(uuid))
                .expectNext(product)
                .verifyComplete();
        verify(persistence).find(uuid);
    }

    @Test
    void shouldFindAllProducts() {
        Paging.Page page = new Paging.Page(0, 10);
        delegate.findAll(page);
        verify(persistence).findAll(page);
    }

    @Test
    void shouldFindAllByOffering() {
        Paging.Page page = new Paging.Page(0, 10);
        String offering = "offering";
        delegate.findAllByOffering(offering, page);
        verify(persistence).findAllByOffering(offering, page);
    }
}
