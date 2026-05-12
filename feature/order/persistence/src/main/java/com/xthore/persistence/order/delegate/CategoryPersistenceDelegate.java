package com.xthore.persistence.order.delegate;

import com.xthore.data.order.persistence.CategoryPersistence;
import com.xthore.domain.order.model.Category;
import com.xthore.persistence.order.datasource.CategoryDatasource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CategoryPersistenceDelegate implements CategoryPersistence {
    private final CategoryDatasource datasource;

    public CategoryPersistenceDelegate(CategoryDatasource datasource) {
        this.datasource = datasource;
    }

    @Override
    public Flux<Category> findAll() {
        return datasource.findAll()
            .map(entity -> new Category(
                entity.name(),
                entity.description(),
                entity.createdAt()
            ));
    }
}
