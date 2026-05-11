package com.xthore.rest.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xthore.common.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.spring.webflux.advice.ProblemHandling;
import org.zalando.problem.spring.webflux.advice.security.SecurityAdviceTrait;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class RequestExceptionHandler implements ProblemHandling, SecurityAdviceTrait {
    @ExceptionHandler(BusinessException.class)
    public Mono<ResponseEntity<Problem>> handleBusinessException(BusinessException error) {
        HttpStatus status;
        try {
            status = HttpStatus.valueOf(error.getCode());
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return Mono.just(ResponseEntity.status(status)
                .body(new Problem(error.getStatus(), error.getCode(), error.getReason())));
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Problem(
            @JsonProperty("title") String title,
            @JsonProperty("status") int status,
            @JsonProperty("data") Object data) {
    }
}
