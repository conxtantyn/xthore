package com.xthore.rest.catalog.controller;

import com.xthore.domain.catalog.usecase.ListProductsUsecase;
import com.xthore.domain.catalog.usecase.RetrieveProductUsecase;
import com.xthore.rest.catalog.TestBootstrap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

@TestBootstrap
class ProductControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RetrieveProductUsecase retrieveProductUsecase;

    @Autowired
    private ListProductsUsecase listProductsUsecase;

    @Test
    void shouldFindProduct() {
        String uuid = "550e8400-e29b-41d4-a716-446655440100";
        webTestClient.get()
                .uri("/product/{uuid}", uuid)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.name").isEqualTo("iPhone 15");
    }
 
    @Test
    void shouldFindAllProducts() {
        webTestClient.get()
                .uri("/product?page=0&size=10")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.items").isArray();
    }
}
