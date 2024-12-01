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
        val restaurant = restaurantRepository.findByIdAndOwnerId(
            restaurantId = Id(restaurantId),
            ownerId = Id(ownerId)
        ) ?: throw IllegalArgumentException("Restaurant not found")

        restaurant.openRestaurant()

        restaurantRepository.save(restaurant).run {
            this.pullEvents().forEach { applicationEventPublisher.publishEvent(it) }
        }
    }
}