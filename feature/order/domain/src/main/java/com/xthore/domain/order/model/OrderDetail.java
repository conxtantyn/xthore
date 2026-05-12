package com.xthore.domain.order.model;

import java.util.List;
import java.util.UUID;

public record OrderDetail(
    UUID order,
    Order.State state,
    String category,
    String customer,
    String site,
    List<Article> articles,
    PaymentMethod paymentMethod
) {}
