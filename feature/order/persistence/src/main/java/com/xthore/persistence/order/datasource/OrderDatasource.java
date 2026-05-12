package com.xthore.persistence.order.datasource;

import com.xthore.persistence.order.entity.OrderEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface OrderDatasource extends ReactiveCrudRepository<OrderEntity, UUID> {
}
