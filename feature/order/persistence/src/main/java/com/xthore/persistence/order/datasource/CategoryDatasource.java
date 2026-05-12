package com.xthore.persistence.order.datasource;

import com.xthore.persistence.order.entity.CategoryEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDatasource extends ReactiveCrudRepository<CategoryEntity, String> {
}
