package com.biuea.tablereservingapplication.application.restaurant

import com.biuea.tablereservingapplication.core.Id
import com.biuea.tablereservingapplication.domain.owner.repository.OwnerRepository
import com.biuea.tablereservingapplication.domain.restaurant.aggregate.RestaurantAggregation
import com.biuea.tablereservingapplication.domain.restaurant.repository.RestaurantRepository
import jakarta.transaction.Transactional
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class RegisterRestaurantFacade(
    private val ownerRepository: OwnerRepository,
    private val restaurantRepository: RestaurantRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    @Transactional
    fun execute(dto: RegisterRestaurantDTO) {
        val owner = ownerRepository.findById(Id(dto.ownerId))
            ?: throw IllegalArgumentException("Owner not found")
        owner.isRegisterAvailableRestaurant()
        this.restaurantRepository.save(dto.mapBy(owner.id))
    }
}

data class RegisterRestaurantDTO(
    val ownerId: Long,
    val name: String,
    val address: String,
    val phoneNumber: String,
    val description: String,
    val category: String
) {
    fun mapBy(ownerId: Id): RestaurantAggregation {
        return RestaurantAggregation.create(
            ownerId = ownerId,
            name = this.name,
            address = this.address,
            phoneNumber = this.phoneNumber,
            description = this.description,
            category = this.category,
            menus = mutableListOf()
        )
    }
}