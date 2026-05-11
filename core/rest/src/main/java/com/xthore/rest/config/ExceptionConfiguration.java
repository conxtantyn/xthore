package com.xthore.rest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.zalando.problem.spring.webflux.advice.security.SecurityProblemSupport;

@Configuration
@Import(SecurityProblemSupport.class)
public class ExceptionConfiguration {
}
