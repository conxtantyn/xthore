package com.xthore.persistence.order.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("xt_category")
public class CategoryEntity implements Persistable<String> {
    @Id
    private final String name;
    private final String description;
    @Column("created_at")
    private final Long createdAt;

    @Transient
    private boolean isNew = true;

    public CategoryEntity(String name, String description, Long createdAt) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    @Override
    public String getId() {
        return name;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public String name() { return name; }
    public String description() { return description; }
    public Long createdAt() { return createdAt; }
}
