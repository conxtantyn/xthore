package com.xthore.persistence.catalog;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EntityScan("com.xthore.persistence.catalog.entity")
@EnableR2dbcRepositories(basePackages = "com.xthore.persistence.catalog.datasource")
@ComponentScan("com.xthore.persistence.catalog.delegate")
public class PersistenceModule {}
