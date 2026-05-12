package com.xthore.domain.order.usecase;

import com.xthore.common.model.Paging;
import com.xthore.common.usecase.PagerWithArgsUsecase;
import com.xthore.domain.order.model.Order;
import com.xthore.domain.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class ListOrdersUsecase implements
        PagerWithArgsUsecase<ListOrdersUsecase.Args, Order> {
    private final OrderRepository orderRepository;

    public ListOrdersUsecase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Paging.Response<Order> execute(Args args) {
        return orderRepository.findAll(args.category(), args.page);
    }

    public record Args(String category, Paging.Page page) {}
}
