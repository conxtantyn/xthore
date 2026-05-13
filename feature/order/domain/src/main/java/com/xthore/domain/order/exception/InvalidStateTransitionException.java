package com.xthore.domain.order.exception;
 
import com.xthore.common.exception.BusinessException;
 
public class InvalidStateTransitionException extends BusinessException {
    public InvalidStateTransitionException() {
        super(400, "Invalid state transition");
    }
}
