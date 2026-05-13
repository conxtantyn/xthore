package com.xthore.rest.order.model;
 
import com.xthore.domain.order.model.Article;
import com.xthore.domain.order.model.Order;
import com.xthore.domain.order.model.PaymentMethod;
 
import java.util.List;
 
public record UpdateModel(
        Order.State state,
        String category,
        CustomerModel customer,
        SiteModel site,
        List<Article> orderItems,
        PaymentMethod paymentMethod
) {}
