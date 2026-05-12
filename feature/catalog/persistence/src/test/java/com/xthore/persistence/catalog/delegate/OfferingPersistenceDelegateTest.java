package com.xthore.persistence.catalog.delegate;

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
public class OfferingPersistenceDelegateTest {

    @Autowired
    private OfferingPersistenceDelegate delegate;

    @Autowired
    private ProductDatasource productDatasource;

    @Autowired
    private OfferingDatasource offeringDatasource;

    @Autowired
    private ProductOfferingDatasource productOfferingDatasource;

    private final String productUuid = UUID.randomUUID().toString();
    private final String productOfferingUuid = UUID.randomUUID().toString();
    private final String offeringName = "Test Offering " + UUID.randomUUID();

    @BeforeEach
    void setUp() {
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
    void shouldFindAllOfferings() {
        StepVerifier.create(delegate.findAll())
            .assertNext(offering -> {
                assertThat(offering.name()).isEqualTo(offeringName);
                assertThat(offering.description()).isEqualTo("Description");
            }).verifyComplete();
    }

    @Test
    void shouldFindOfferingByProduct() {
        StepVerifier.create(delegate.findByProduct(productUuid))
            .assertNext(offering -> {
                assertThat(offering.name()).isEqualTo(offeringName);
            }).verifyComplete();
    }

    @Test
    void shouldCheckAvailability() {
        StepVerifier.create(delegate.isAvailable(productOfferingUuid))
            .expectNext(true)
            .verifyComplete();
        StepVerifier.create(delegate.isAvailable(UUID.randomUUID().toString()))
            .expectNext(false)
            .verifyComplete();
    }
}
