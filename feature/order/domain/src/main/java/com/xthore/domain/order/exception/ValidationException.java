package com.xthore.domain.order.exception;
 
import com.xthore.common.exception.BusinessException;
 
public class ValidationException extends BusinessException {
    public ValidationException() {
        super(400, "Validation failed: required fields missing or invalid");
    }
}
