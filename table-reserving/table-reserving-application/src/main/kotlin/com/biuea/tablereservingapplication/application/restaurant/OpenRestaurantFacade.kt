package com.biuea.tablereservingapplication.application.restaurant

import com.biuea.tablereservingapplication.core.Id
import com.biuea.tablereservingapplication.domain.owner.repository.OwnerRepository
import com.biuea.tablereservingapplication.domain.restaurant.repository.RestaurantRepository
import jakarta.transaction.Transactional
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class OpenRestaurantFacade(
    private val restaurantRepository: RestaurantRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    @Transactional
    fun execute(
        ownerId: Long,
        restaurantId: Long
    ) {
        val restaurant = restaurantRepository.findById(
            restaurantId = Id(restaurantId)
        ) ?: throw IllegalArgumentException("Restaurant not found")

        restaurant.openRestaurant(
            publish = applicationEventPublisher::publishEvent,
            ownerId = Id(ownerId)
        )

        restaurantRepository.save(restaurant)
    }
}