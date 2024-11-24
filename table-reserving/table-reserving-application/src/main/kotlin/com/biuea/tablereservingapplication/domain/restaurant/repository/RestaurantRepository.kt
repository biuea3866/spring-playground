package com.biuea.tablereservingapplication.domain.restaurant.repository

import com.biuea.tablereservingapplication.core.Id
import com.biuea.tablereservingapplication.domain.restaurant.model.RestaurantAggregation

interface RestaurantRepository {
    fun save(restaurant: RestaurantAggregation): RestaurantAggregation
    fun findByIdAndOwnerId(
        restaurantId: Id,
        ownerId: Id
    ): RestaurantAggregation?
}