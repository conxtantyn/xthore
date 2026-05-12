package com.xthore.persistence.order.delegate;

import com.xthore.persistence.order.PersistenceModule;
import com.xthore.persistence.order.datasource.ArticleDatasource;
import com.xthore.persistence.order.datasource.CategoryDatasource;
import com.xthore.persistence.order.datasource.OrderDatasource;
import com.xthore.persistence.order.entity.CategoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CategoryPersistenceDelegateTest {

    @Autowired
    private CategoryPersistenceDelegate delegate;

    @Autowired
    private CategoryDatasource categoryDatasource;

    @Autowired
    private OrderDatasource orderDatasource;

    @Autowired
    private ArticleDatasource articleDatasource;

    @BeforeEach
    void setUp() {
        articleDatasource.deleteAll().block();
        orderDatasource.deleteAll().block();
        categoryDatasource.deleteAll().block();
        
        categoryDatasource.save(new CategoryEntity(
                "CAT1",
                "Description 1",
                System.currentTimeMillis())
        ).block();
        categoryDatasource.save(new CategoryEntity(
                "CAT2",
                "Description 2",
                System.currentTimeMillis())
        ).block();
    }

    @Test
    void shouldFindAllCategories() {
        StepVerifier.create(delegate.findAll())
            .assertNext(category -> {
                assertThat(category.name()).isEqualTo("CAT1");
                assertThat(category.description()).isEqualTo("Description 1");
            })
            .assertNext(category -> {
                assertThat(category.name()).isEqualTo("CAT2");
                assertThat(category.description()).isEqualTo("Description 2");
            }).verifyComplete();
    }
}
