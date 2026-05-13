package com.xthore.domain.order.exception;
 
import com.xthore.common.exception.BusinessException;
 
public class OrderNotFoundException extends BusinessException {
    public OrderNotFoundException() {
        super(404, "Order not found");
    }
}
