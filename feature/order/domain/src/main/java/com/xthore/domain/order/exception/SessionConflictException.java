package com.xthore.domain.order.exception;
 
import com.xthore.common.exception.BusinessException;
 
public class SessionConflictException extends BusinessException {
    public SessionConflictException() {
        super(409, "Idempotency key conflict: different payload for the same key");
    }
}
