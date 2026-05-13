package com.xthore.rest.config;

import com.xthore.rest.handler.ResponseHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;

@Configuration
public class RestConfiguration {
    @Bean
    public ResponseBodyResultHandler unifiedResponseBodyResultHandler(
            ServerCodecConfigurer codecConfigurer,
            RequestedContentTypeResolver resolver,
            ReactiveAdapterRegistry registry) {
        return new ResponseHandler(codecConfigurer.getWriters(), resolver, registry);
    }
}
