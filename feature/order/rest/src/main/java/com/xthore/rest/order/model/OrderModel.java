package com.xthore.rest.order.model;
 
import com.xthore.domain.order.model.Article;
import com.xthore.domain.order.model.PaymentMethod;
 
import java.util.List;
import java.util.Objects;
 
public record OrderModel(
        String category,
        CustomerModel customer,
        SiteModel site,
        List<Article> orderItems,
        PaymentMethod paymentMethod
) {
    @Override
    public int hashCode() {
        return Objects.hash(category, customer, site, orderItems, paymentMethod);
    }
}
