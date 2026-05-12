package com.xthore.persistence.catalog.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Table("xt_product")
public class ProductEntity implements Persistable<UUID> {
    @Id
    @Column("uuid")
    private final UUID uuid;
    private final String name;
    private final BigDecimal price;
    @Column("created_at")
    private final Long createdAt;

    @Transient
    private boolean isNew = true;

    public ProductEntity(UUID uuid, String name, BigDecimal price, Long createdAt) {
        this.uuid = uuid;
        this.name = name;
        this.price = price;
        this.createdAt = createdAt;
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public UUID uuid() { return uuid; }
    public String name() { return name; }
    public BigDecimal price() { return price; }
    public Long createdAt() { return createdAt; }
}
