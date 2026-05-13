package com.xthore.domain.order.exception;
 
import com.xthore.common.exception.BusinessException;
 
public class ProductNotFoundException extends BusinessException {
    public ProductNotFoundException() {
        super(404, "Product uuid not found in catalog");
    }
}
