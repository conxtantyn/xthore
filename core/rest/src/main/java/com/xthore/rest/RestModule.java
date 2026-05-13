package com.xthore.rest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
    basePackages = {
        RestModule.HANDLER,
        RestModule.CONFIG,
    }
)
public class RestModule {
    public static final String CONFIG = "com.xthore.rest.config";
    public static final String HANDLER = "com.xthore.rest.handler";
}
