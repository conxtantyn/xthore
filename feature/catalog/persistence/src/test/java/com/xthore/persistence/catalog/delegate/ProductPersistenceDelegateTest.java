package com.xthore.persistence.catalog.delegate;

import com.xthore.common.model.Paging;
import com.xthore.persistence.catalog.datasource.OfferingDatasource;
import com.xthore.persistence.catalog.datasource.ProductDatasource;
import com.xthore.persistence.catalog.datasource.ProductOfferingDatasource;
import com.xthore.persistence.catalog.entity.OfferingEntity;
import com.xthore.persistence.catalog.entity.ProductEntity;
import com.xthore.persistence.catalog.entity.ProductOfferingEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductPersistenceDelegateTest {

    @Autowired
    private ProductPersistenceDelegate delegate;

    @Autowired
    private ProductDatasource productDatasource;

    @Autowired
    private OfferingDatasource offeringDatasource;

    @Autowired
    private ProductOfferingDatasource productOfferingDatasource;

    private final String productUuid = UUID.randomUUID().toString();
    private final String offeringName = "Test Offering " + UUID.randomUUID();
    private final String productOfferingUuid = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        // Order of deletion matters due to FK constraints
        productOfferingDatasource.deleteAll().block();
        productDatasource.deleteAll().block();
        offeringDatasource.deleteAll().block();

        productDatasource.save(new ProductEntity(
                UUID.fromString(productUuid),
                "Test Product",
                BigDecimal.valueOf(100.00),
                System.currentTimeMillis())
        ).block();
        offeringDatasource.save(new OfferingEntity(
                offeringName,
                "Description",
                BigDecimal.valueOf(0.10),
                System.currentTimeMillis())
        ).block();
        productOfferingDatasource.save(new ProductOfferingEntity(
                UUID.fromString(productOfferingUuid),
                offeringName,
                UUID.fromString(productUuid),
                System.currentTimeMillis())
        ).block();
    }

    @Test
    void shouldFindProductByProductOfferingUuid() {
        StepVerifier.create(delegate.find(productOfferingUuid))
            .assertNext(product -> {
                assertThat(product.id()).isEqualTo(productUuid);
                assertThat(product.name()).isEqualTo("Test Product");
                assertThat(product.price()).isEqualTo(100.00);
            }).verifyComplete();
    }

    @Test
    void shouldFindAllProducts() {
        StepVerifier.create(delegate.findAll(new Paging.Page(0, 10)).items())
            .assertNext(product -> {
                assertThat(product.id()).isEqualTo(productUuid);
            }).verifyComplete();
    }

    @Test
    void shouldFindAllProductsByOffering() {
        StepVerifier.create(delegate.findAllByOffering(offeringName, new Paging.Page(0, 10))
                        .items()).assertNext(product -> {
                            assertThat(product.id()).isEqualTo(productUuid);
                        }).verifyComplete();
    }
}
