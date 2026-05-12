package com.xthore.domain.order.repository;

import com.xthore.domain.order.model.Category;
import reactor.core.publisher.Flux;

public interface CategoryRepository {
    Flux<Category> findAll();
}
