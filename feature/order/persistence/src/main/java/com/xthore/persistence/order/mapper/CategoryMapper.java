package com.xthore.persistence.order.mapper;

import com.xthore.domain.order.model.Category;
import com.xthore.persistence.order.entity.CategoryEntity;

public class CategoryMapper {
    public static Category mapToDomain(CategoryEntity category) {
        return new Category(
                category.name(),
                category.description(),
                category.createdAt()
        );
    }
}
