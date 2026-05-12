package com.xthore.persistence.order.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;

@Table("xt_article")
public record ArticleEntity(
    @Id
    UUID uuid,
    @Column("order")
    UUID orderUuid,
    String offering,
    Integer quantity
) {}
