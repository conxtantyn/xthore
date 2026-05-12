package com.xthore.persistence.catalog.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;

@Table("xt_offering")
public class OfferingEntity implements Persistable<String> {
    @Id
    private final String name;
    private final String description;
    private final BigDecimal discount;
    @Column("created_at")
    private final Long createdAt;

    @Transient
    private boolean isNew = true;

    public OfferingEntity(String name, String description, BigDecimal discount, Long createdAt) {
        this.name = name;
        this.description = description;
        this.discount = discount;
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
    public BigDecimal discount() { return discount; }
    public Long createdAt() { return createdAt; }
}
