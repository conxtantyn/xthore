package com.xthore.rest.order.controller;

import com.xthore.domain.order.model.*;
import com.xthore.rest.order.TestBootstrap;
import com.xthore.rest.order.model.*;
import com.xthore.remote.order.client.OfferingClient;
import com.xthore.remote.order.model.Offering;
import com.xthore.remote.model.RemoteResponse;
 
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Map;
import java.util.UUID;
 
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestBootstrap
class OrderControllerTest {
 
    @Autowired
    private WebTestClient webTestClient;
 
    @MockBean
    private OfferingClient offeringClient;

    @Test
    void shouldCreateOrder() {
        when(offeringClient.get(anyString()))
                .thenReturn(new RemoteResponse<>(new Offering(
                    "po-1",
                    "Product 1",
                    System.currentTimeMillis()
                )));
        OrderModel request = new OrderModel(
                "B2B",
                new CustomerModel("c7b3b4b5-4b5b-4b5b-4b5b-4b5b5b5b5b5b"),
                new SiteModel("site-1"),
                List.of(new Article("po-1", 2)),
                new PaymentMethod(PaymentMethod.Type.INVOICE, null)
        );
        webTestClient.post()
                .uri("/customer-orders")
                .header("Idempotency-Key", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.id").exists()
                .jsonPath("$.data.state").isEqualTo("DRAFT");
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldFindOrderById() {
        when(offeringClient.get(anyString()))
                .thenReturn(new RemoteResponse<>(new Offering(
                    "po-1",
                    "Product 1",
                    System.currentTimeMillis()
                )));
        OrderModel request = new OrderModel(
                "B2C",
                new CustomerModel("c7b3b4b5-4b5b-4b5b-4b5b-4b5b5b5b5b5b"),
                new SiteModel("site-2"),
                List.of(new Article("po-1", 1)),
                new PaymentMethod(PaymentMethod.Type.INVOICE, null)
        );
        Map<String, Object> response = webTestClient.post()
                .uri("/customer-orders")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(java.util.Map.class)
                .returnResult()
                .getResponseBody();
        assert response != null;
        Map<String, Object> data = (Map<String, Object>) response.get("data");
        String orderId = (String) data.get("id");
        webTestClient.get()
                .uri("/customer-orders/{id}", orderId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.id").isEqualTo(orderId);
    }

    @Test
    void shouldListOrders() {
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                    .path("/customer-orders")
                    .queryParam("offset", "0")
                    .queryParam("limit", "10")
                    .build())
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.data.items").isArray();
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldPatchOrderState() {
        when(offeringClient.get(anyString()))
                .thenReturn(new RemoteResponse<>(new Offering(
                    "po-1",
                    "Product 1",
                    System.currentTimeMillis()
                )));
        OrderModel createRequest = new OrderModel(
                "B2B",
                new CustomerModel("c7b3b4b5-4b5b-4b5b-4b5b-4b5b5b5b5b5b"),
                new SiteModel("site-3"),
                List.of(new Article("po-1", 1)),
                new PaymentMethod(PaymentMethod.Type.INVOICE, null)
        );
        Map<String, Object> response = webTestClient.post()
                .uri("/customer-orders")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Map.class)
                .returnResult()
                .getResponseBody();
        assert response != null;
        Map<String, Object> data = (Map<String, Object>) response.get("data");
        String orderId = (String) data.get("id");
        UpdateModel updateRequest = new UpdateModel(
                Order.State.PREVIEW,
                null,
                null,
                null,
                null,
                null
        );
        webTestClient.patch()
                .uri("/customer-orders/{id}", orderId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.state").isEqualTo("PREVIEW");
    }
}
