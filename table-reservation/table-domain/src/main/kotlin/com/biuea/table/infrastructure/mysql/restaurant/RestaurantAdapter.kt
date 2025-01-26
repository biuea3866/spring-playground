package com.biuea.table.infrastructure.mysql.restaurant

import com.biuea.table.domain.restaurant.ReservationRelation
import com.biuea.table.domain.restaurant.RestaurantEntity
import com.biuea.table.domain.restaurant.RestaurantRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class RestaurantAdapter(
    private val restaurantJpaRepository: RestaurantJpaRepository
): RestaurantRepository {
    override fun findBy(id: Long): RestaurantEntity? {
        return restaurantJpaRepository.findByIdOrNull(id)
    }
}