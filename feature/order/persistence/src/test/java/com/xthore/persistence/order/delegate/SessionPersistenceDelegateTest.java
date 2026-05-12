package com.xthore.persistence.order.delegate;

import com.xthore.domain.order.model.Session;
import com.xthore.persistence.order.PersistenceModule;
import com.xthore.persistence.order.datasource.ArticleDatasource;
import com.xthore.persistence.order.datasource.OrderDatasource;
import com.xthore.persistence.order.datasource.SessionDatasource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Import(PersistenceModule.class)
public class SessionPersistenceDelegateTest {

    @Autowired
    private SessionPersistenceDelegate delegate;

    @Autowired
    private SessionDatasource sessionDatasource;

    @Autowired
    private OrderDatasource orderDatasource;

    @Autowired
    private ArticleDatasource articleDatasource;

    @BeforeEach
    void setUp() {
        articleDatasource.deleteAll().block();
        orderDatasource.deleteAll().block();
        sessionDatasource.deleteAll().block();
    }

    @Test
    void shouldSaveAndRetrieveSession() {
        String key = "test-key";
        UUID orderId = UUID.randomUUID();
        Session session = new Session(key, "hash", orderId, System.currentTimeMillis());

        StepVerifier.create(delegate.save(session))
            .assertNext(saved -> {
                assertThat(saved.key()).isEqualTo(key);
                assertThat(saved.request()).isEqualTo("hash");
                assertThat(saved.order()).isEqualTo(orderId);
            }).verifyComplete();
        StepVerifier.create(delegate.findByKey(key))
            .assertNext(retrieved -> {
                assertThat(retrieved.key()).isEqualTo(key);
                assertThat(retrieved.request()).isEqualTo("hash");
                assertThat(retrieved.order()).isEqualTo(orderId);
            }).verifyComplete();
    }
}
