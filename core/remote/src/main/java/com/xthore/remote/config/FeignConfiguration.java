package com.xthore.remote.config;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class FeignConfiguration {
    @Bean
    public HttpMessageConverters messageConverters() {
        return new HttpMessageConverters(new MappingJackson2HttpMessageConverter());
    }
}
