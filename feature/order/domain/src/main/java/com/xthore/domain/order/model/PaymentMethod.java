package com.xthore.domain.order.model;

public record PaymentMethod(
    Type type,
    String iban
) {
    public enum Type {
        DIRECT_DEBIT, INVOICE
    }
}
