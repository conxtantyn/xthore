package com.xthore.persistence.order.mapper;

import com.xthore.domain.order.model.Article;
import com.xthore.persistence.order.entity.ArticleEntity;

import java.util.UUID;

public class ArticleMapper {
    public static ArticleEntity mapToDomain(UUID order, Article article) {
        return new ArticleEntity(
                UUID.randomUUID(),
                order,
                article.offering(),
                article.quantity()
        );
    }
}
