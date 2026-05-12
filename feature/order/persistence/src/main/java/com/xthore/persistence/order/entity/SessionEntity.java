package com.xthore.persistence.order.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;

@Table("xt_session")
public class SessionEntity implements Persistable<String> {
    @Id
    private final String key;
    @Column("request")
    private final String requestHash;
    @Column("\"order\"")
    private final UUID orderId;
    @Column("created_at")
    private final Long createdAt;

    @Transient
    private boolean isNew = true;

    public SessionEntity(String key, String requestHash, UUID orderId, Long createdAt) {
        this.key = key;
        this.requestHash = requestHash;
        this.orderId = orderId;
        this.createdAt = createdAt;
    }

    @Override
    public String getId() {
        return key;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public String key() { return key; }
    public String requestHash() { return requestHash; }
    public UUID orderId() { return orderId; }
    public Long createdAt() { return createdAt; }
}
