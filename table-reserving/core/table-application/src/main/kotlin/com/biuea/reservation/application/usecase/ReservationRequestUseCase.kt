package com.biuea.reservation.application.usecase

import com.biuea.reservation.domain.domain.CustomerType
import com.biuea.reservation.domain.domain.ReservationEntity
import com.biuea.reservation.domain.domain.RestaurantType
import java.time.ZonedDateTime

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
        val startAt: ZonedDateTime,
        val endAt: ZonedDateTime?
    ) {
        fun toReservationEntity(reservationNumber: Int): ReservationEntity {
            return ReservationEntity.request(
                customerId = customerId,
                customerType = customerType,
                customerName = customerName,
                restaurantId = restaurantId,
                restaurantType = restaurantType,
                restaurantName = restaurantName,
                aPartyOf = aPartyOf,
                startAt = startAt,
                endAt = endAt,
                reservationNumber = reservationNumber
            )
        }
    }
}