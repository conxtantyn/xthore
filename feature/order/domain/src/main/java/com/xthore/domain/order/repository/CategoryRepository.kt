package com.xthore.domain.order.repository

import com.xthore.domain.order.model.Category
import reactor.core.publisher.Flux

interface CategoryRepository {
    fun findAll(): Flux<Category>
}
