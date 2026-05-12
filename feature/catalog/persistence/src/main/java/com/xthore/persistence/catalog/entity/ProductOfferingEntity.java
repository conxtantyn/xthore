package com.xthore.persistence.catalog.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;

@Table("xt_product_offering")
public class ProductOfferingEntity implements Persistable<UUID> {
    @Id
    @Column("uuid")
    private final UUID uuid;
    private final String offering;
    private final UUID product;
    @Column("created_at")
    private final Long createdAt;

    @Transient
    private boolean isNew = true;

    public ProductOfferingEntity(UUID uuid, String offering, UUID product, Long createdAt) {
        this.uuid = uuid;
        this.offering = offering;
        this.product = product;
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
    public String offering() { return offering; }
    public UUID product() { return product; }
    public Long createdAt() { return createdAt; }
}
