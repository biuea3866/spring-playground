package com.biuea.tablereservingapplication.application.restaurant

import com.biuea.tablereservingapplication.domain.restaurant.repository.RestaurantRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class AddMenuFacade(
    private val restaurantRepository: RestaurantRepository,
) {
    @Transactional
    fun execute() {}
}