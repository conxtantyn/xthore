package com.xthore.persistence.order.delegate;

import com.xthore.common.model.Paging;
import com.xthore.domain.order.model.Article;
import com.xthore.domain.order.model.Order;
import com.xthore.domain.order.model.PaymentMethod;
import com.xthore.persistence.order.PersistenceModule;
import com.xthore.persistence.order.datasource.ArticleDatasource;
import com.xthore.persistence.order.datasource.CategoryDatasource;
import com.xthore.persistence.order.datasource.CustomerDatasource;
import com.xthore.persistence.order.datasource.OrderDatasource;
import com.xthore.persistence.order.entity.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Import(PersistenceModule.class)
public class OrderPersistenceDelegateTest {

    @Autowired
    private OrderPersistenceDelegate delegate;

    @Autowired
    private CategoryDatasource categoryDatasource;

    @Autowired
    private CustomerDatasource customerDatasource;

    @Autowired
    private OrderDatasource orderDatasource;

    @Autowired
    private ArticleDatasource articleDatasource;

    private final UUID customerId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        articleDatasource.deleteAll().block();
        orderDatasource.deleteAll().block();
        customerDatasource.deleteAll().block();
        categoryDatasource.deleteAll().block();

        categoryDatasource.save(new com.xthore.persistence.order.entity.CategoryEntity("B2B", "B2B description", System.currentTimeMillis())).block();
        categoryDatasource.save(new com.xthore.persistence.order.entity.CategoryEntity("B2C", "B2C description", System.currentTimeMillis())).block();

        CustomerEntity customer = new CustomerEntity(
            customerId,
            "DIRECT_DEBIT",
            "IBAN123",
            System.currentTimeMillis()
        );
        customerDatasource.save(customer).block();
    }

    @Test
    void shouldSaveAndRetrieveOrder() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order(
            orderId,
            Order.State.DRAFT,
            "B2B",
            customerId.toString(),
            "berlin-1",
            List.of(new Article("pro-1", 1)),
            new PaymentMethod(PaymentMethod.Type.DIRECT_DEBIT, "IBAN123"),
            System.currentTimeMillis(),
            System.currentTimeMillis()
        );
        StepVerifier.create(delegate.save(order))
            .assertNext(saved -> {
                assertThat(saved.id()).isEqualTo(orderId);
                assertThat(saved.state()).isEqualTo(Order.State.DRAFT);
                assertThat(saved.articles()).hasSize(1);
                assertThat(saved.articles().get(0).offering()).isEqualTo("pro-1");
            }).verifyComplete();
        StepVerifier.create(delegate.findById(orderId))
            .assertNext(retrieved -> {
                assertThat(retrieved.id()).isEqualTo(orderId);
                assertThat(retrieved.category()).isEqualTo("B2B");
                assertThat(retrieved.customer()).isEqualTo(customerId.toString());
            }).verifyComplete();
    }

    @Test
    void shouldFindAllWithCategoryFilter() {
        UUID o1 = UUID.randomUUID();
        UUID o2 = UUID.randomUUID();
        
        Order order1 = createOrder(o1, "B2B");
        Order order2 = createOrder(o2, "B2C");

        delegate.save(order1).block();
        delegate.save(order2).block();

        Paging.Page page = new Paging.Page(0, 10);
        Paging.Response<Order> response = delegate.findAll("B2B", page);
        
        StepVerifier.create(response.items())
            .expectNextMatches(o -> o.id().equals(o1))
            .verifyComplete();
        StepVerifier.create(response.pageable())
            .assertNext(p -> assertThat(p.total()).isGreaterThanOrEqualTo(1))
            .verifyComplete();
    }

    private Order createOrder(UUID id, String category) {
        return new Order(
            id,
            Order.State.DRAFT,
            category,
            customerId.toString(),
            "site",
            List.of(),
            new PaymentMethod(PaymentMethod.Type.DIRECT_DEBIT, "IBAN123"),
            System.currentTimeMillis(),
            System.currentTimeMillis()
        );
    }
}
