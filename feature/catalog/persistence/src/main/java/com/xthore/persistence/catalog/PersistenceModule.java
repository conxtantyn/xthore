package com.xthore.persistence.catalog;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@ComponentScan("com.xthore.persistence.catalog.delegate")
@EnableR2dbcRepositories(basePackages = "com.xthore.persistence.catalog.datasource")
public class PersistenceModule {
}
