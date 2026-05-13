package com.xthore.domain.order.exception;
 
import com.xthore.common.exception.BusinessException;
 
public class OrderImmutableException extends BusinessException {
    public OrderImmutableException() {
        super(400, "Order is immutable in its current state");
    }
}
