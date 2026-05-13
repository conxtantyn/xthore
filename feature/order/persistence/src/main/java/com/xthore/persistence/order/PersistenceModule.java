package com.xthore.persistence.order;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EntityScan("com.xthore.persistence.order.entity")
@EnableR2dbcRepositories(basePackages = "com.xthore.persistence.order.datasource")
@ComponentScan("com.xthore.persistence.order.delegate")
public class PersistenceModule {}
