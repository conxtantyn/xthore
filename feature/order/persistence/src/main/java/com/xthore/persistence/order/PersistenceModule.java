package com.xthore.persistence.order;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@ComponentScan("com.xthore.persistence.order.delegate")
@EnableR2dbcRepositories(basePackages = "com.xthore.persistence.order.datasource")
public class PersistenceModule {}
