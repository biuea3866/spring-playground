package com.biuea.tablereservingapplication.infrastructure.mysql.restaurant.jpa

import com.biuea.tablereservingapplication.core.Id
import com.biuea.tablereservingapplication.domain.restaurant.aggregate.RestaurantAggregation
import com.biuea.tablereservingapplication.domain.restaurant.repository.RestaurantRepository
import org.springframework.stereotype.Component

@Component
class RestaurantRepositoryImpl(
    private val restaurantJpaRepository: RestaurantJpaRepository
): RestaurantRepository {
    override fun findById(restaurantId: Id): RestaurantAggregation? {
        return this.restaurantJpaRepository.findById(restaurantId.id)
            .orElse(null)
            ?.toAggregate()
    }

    override fun save(restaurant: RestaurantAggregation): RestaurantAggregation {
        TODO("Not yet implemented")
    }

    override fun findByIdAndOwnerId(
        restaurantId: Id,
        ownerId: Id
    ): RestaurantAggregation? {
        TODO("Not yet implemented")
    }
}