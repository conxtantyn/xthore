package com.xthore.data.order.persistence

import com.xthore.domain.order.model.Category
import reactor.core.publisher.Flux

interface CategoryPersistence {
    fun findAll(): Flux<Category>
}
