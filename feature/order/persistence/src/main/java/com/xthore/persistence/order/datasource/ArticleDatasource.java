package com.xthore.persistence.order.datasource;

import com.xthore.persistence.order.entity.ArticleEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import reactor.core.publisher.Flux;

@Repository
public interface ArticleDatasource extends ReactiveCrudRepository<ArticleEntity, UUID> {
    Flux<ArticleEntity> findByOrderUuid(UUID orderUuid);
}
