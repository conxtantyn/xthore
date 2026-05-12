package com.xthore.domain.order.model;

import java.util.UUID;

public record Session(
    String key,
    String request,
    UUID order,
    Long createdAt
) {}
