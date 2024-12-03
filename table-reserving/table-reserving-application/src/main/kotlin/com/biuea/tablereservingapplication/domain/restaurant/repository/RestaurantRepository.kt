package com.biuea.tablereservingapplication.domain.restaurant.repository

import com.biuea.tablereservingapplication.core.Id
import com.biuea.tablereservingapplication.domain.restaurant.aggregate.RestaurantAggregation

interface RestaurantRepository {
    fun findById(restaurantId: Id): RestaurantAggregation?
    fun save(restaurant: RestaurantAggregation): RestaurantAggregation
    fun findByIdAndOwnerId(
        restaurantId: Id,
        ownerId: Id
    ): RestaurantAggregation?
}