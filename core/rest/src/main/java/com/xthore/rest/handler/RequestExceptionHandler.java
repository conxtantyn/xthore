package com.xthore.rest.handler;

import com.xthore.common.exception.BusinessException;
import com.xthore.rest.mapper.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@ControllerAdvice
public class RequestExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleBusinessException(BusinessException error,
            ServerWebExchange exchange) {
        Map<String, Object> body = Response.map(
                error.getReason(),
                exchange.getRequest().getPath().value(),
                error.getCode(),
                exchange.getRequest().getId(),
                error.getStatus());
        return Mono.just(ResponseEntity.status(error.getCode()).body(body));
    }
}
