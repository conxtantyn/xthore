package com.xthore.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

@Configuration
@ComponentScan(
    basePackages = {
        RestModule.EXCEPTION,
        RestModule.CONFIG,
    }
)
public class RestModule {
    public static final String CONFIG = "com.xthore.rest.config";
    public static final String EXCEPTION = "com.xthore.rest.exception";

    @Bean
    public ProblemModule problemModule() {
        return new ProblemModule();
    }

    @Bean
    public ConstraintViolationProblemModule constraintViolationProblemModule() {
        return new ConstraintViolationProblemModule();
    }
}
