package com.xthore.rest.order.controller;
 
import com.xthore.common.model.Paging;
import com.xthore.domain.order.model.Order;
import com.xthore.domain.order.model.OrderDetail;
import com.xthore.domain.order.usecase.*;
import com.xthore.rest.order.model.OrderModel;
import com.xthore.rest.order.model.UpdateModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
 
import java.util.Map;
import java.util.UUID;
 
@RestController
@RequestMapping("/customer-orders")
public class OrderController {
    private final CreateOrderUsecase createOrderUsecase;
    private final RetrieveOrderUsecase retrieveOrderUsecase;
    private final ListOrdersUsecase listOrdersUsecase;
    private final UpdateOrderUsecase updateOrderUsecase;
 
    public OrderController(
            CreateOrderUsecase createOrderUsecase,
            RetrieveOrderUsecase retrieveOrderUsecase,
            ListOrdersUsecase listOrdersUsecase,
            UpdateOrderUsecase updateOrderUsecase
    ) {
        this.createOrderUsecase = createOrderUsecase;
        this.retrieveOrderUsecase = retrieveOrderUsecase;
        this.listOrdersUsecase = listOrdersUsecase;
        this.updateOrderUsecase = updateOrderUsecase;
    }
 
    @PostMapping
    public Mono<Order> create(
            @RequestBody OrderModel request,
            @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey
    ) {
        String session = idempotencyKey != null ? idempotencyKey : UUID.randomUUID().toString();
        String payloadHash = String.valueOf(request.hashCode());
        return createOrderUsecase.execute(new CreateOrderUsecase.Args(
                request.category(),
                request.customer() != null ? request.customer().id() : null,
                request.site() != null ? request.site().id() : null,
                request.orderItems(),
                request.paymentMethod(),
                session,
                payloadHash
        ));
    }
 
    @GetMapping("/{id}")
    public Mono<Order> findById(@PathVariable UUID id) {
        return retrieveOrderUsecase.execute(id);
    }
 
    @GetMapping
    public Mono<Map<String, Object>> findAll(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "20") Integer limit
    ) {
        Paging.Response<Order> response = listOrdersUsecase.execute(new ListOrdersUsecase.Args(
                category,
                new Paging.Page(offset, limit)
        ));
        return Mono.zip(response.pageable(), response.items().collectList())
                .map(tuple -> Map.of(
                        "pageable", tuple.getT1(),
                        "items", tuple.getT2()
                ));
    }
 
    @PatchMapping("/{id}")
    public Mono<Order> update(@PathVariable UUID id, @RequestBody UpdateModel request) {
        return updateOrderUsecase.execute(new OrderDetail(
                id,
                request.state(),
                request.category(),
                request.customer() != null ? request.customer().id() : null,
                request.site() != null ? request.site().id() : null,
                request.orderItems(),
                request.paymentMethod()
        ));
    }
}
