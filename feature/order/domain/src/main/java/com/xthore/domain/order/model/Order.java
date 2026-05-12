package com.xthore.domain.order.model;

import java.util.List;
import java.util.UUID;

public record Order(
    UUID id,
    State state,
    String category,
    String customer,
    String site,
    List<Article> articles,
    PaymentMethod paymentMethod,
    Long createdAt,
    Long updatedAt
) {
    public enum State {
        DRAFT, PREVIEW, SUBMITTED, CONFIRMED
    }
}
