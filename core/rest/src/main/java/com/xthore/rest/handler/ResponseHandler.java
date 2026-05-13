package com.xthore.rest.handler;
 
import com.xthore.rest.mapper.Response;
import org.springframework.core.Ordered;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import java.util.List;
import java.util.Map;
 
public class ResponseHandler extends ResponseBodyResultHandler {
 
    public ResponseHandler(List<HttpMessageWriter<?>> writers, 
                           RequestedContentTypeResolver resolver,
                           ReactiveAdapterRegistry registry) {
        super(writers, resolver, registry);
        setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
 
    @Override
    public boolean supports(HandlerResult result) {
        String className = result.getReturnTypeSource().getDeclaringClass().getName();
        return super.supports(result)
                && !className.contains("springdoc")
                && !className.contains("swagger");
    }
 
    @Override
    @SuppressWarnings("unchecked")
    public @NonNull Mono<Void> handleResult(
            @NonNull ServerWebExchange exchange,
            HandlerResult result
    ) {
        Object body = result.getReturnValue();
        Mono<Object> bodyMono;
        if (body instanceof Mono) {
            bodyMono = (Mono<Object>) body;
        } else if (body instanceof Flux) {
            bodyMono = ((Flux<?>) body).collectList().map(list -> list);
        } else {
            bodyMono = Mono.justOrEmpty(body);
        }
        Mono<Map<String, Object>> wrappedMono = bodyMono
            .defaultIfEmpty(Map.of())
            .map(data -> {
                if (data instanceof Map && ((Map<?, ?>) data).containsKey("timestamp")) {
                    return (Map<String, Object>) data;
                }
                int status = exchange.getResponse().getStatusCode() != null
                        ? exchange.getResponse().getStatusCode().value() : 200;
                return Response.map(
                        data,
                        exchange.getRequest().getPath().value(),
                        status,
                        exchange.getRequest().getId()
                );
            });
        return writeBody(wrappedMono, result.getReturnTypeSource(), exchange);
    }
}
