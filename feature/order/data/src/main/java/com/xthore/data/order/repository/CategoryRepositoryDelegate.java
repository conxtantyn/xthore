package com.xthore.data.order.repository;

import com.xthore.data.order.persistence.CategoryPersistence;
import com.xthore.domain.order.model.Category;
import com.xthore.domain.order.repository.CategoryRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class CategoryRepositoryDelegate implements CategoryRepository {
    private final CategoryPersistence persistence;

    public CategoryRepositoryDelegate(CategoryPersistence persistence) {
        this.persistence = persistence;
    }

    @Override
    public Flux<Category> findAll() {
        return persistence.findAll();
    }
}
