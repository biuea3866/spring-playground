package com.biuea.reservation.application.usecase

import com.biuea.reservation.domain.CustomerType
import com.biuea.reservation.domain.ReservationEntity
import com.biuea.reservation.domain.RestaurantType

interface ReservationRequestUseCase {
    fun execute(command: ReservationRequest)

    data class ReservationRequest(
        val customerId: Long,
        val customerType: CustomerType,
        val customerName: String,
        val restaurantId: Long,
        val restaurantType: RestaurantType,
        val restaurantName: String,
        val aPartyOf: Int,
    ) {
        fun toReservationEntity(): ReservationEntity {
            return ReservationEntity.request(
                customerId = customerId,
                customerType = customerType,
                customerName = customerName,
                restaurantId = restaurantId,
                restaurantType = restaurantType,
                restaurantName = restaurantName,
                aPartyOf = aPartyOf
            )
        }
    }
}