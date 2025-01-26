package com.biuea.table.application

import com.biuea.table.application.support.ReservationCoordinator
import com.biuea.table.domain.reservation.ReservationEntity
import com.biuea.table.domain.reservation.ReservationRepository
import com.biuea.table.domain.restaurant.RestaurantRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.ZonedDateTime

@Component
class ReservationRequestApplication(
    private val restaurantRepository: RestaurantRepository,
    private val reservationRepository: ReservationRepository
) {
    @Transactional
    fun execute(command: ReservationRequest) {
        val restaurant = restaurantRepository.findBy(command.restaurantId)
            ?: throw IllegalArgumentException("Restaurant not found")
        val notEntranceCustomers = reservationRepository.findBy(
            entrance = false,
            ids = restaurant.reservationRelations.map { it.reservationId }.toSet()
        )
        val reservation = command.toReservationEntity()
        ReservationCoordinator().issueWaitingNumber(reservation, restaurant, notEntranceCustomers.size)
        reservationRepository.save(reservation)
    }
}

data class ReservationRequest(
    val customerId: Long,
    val restaurantId: Long,
    val aPartyOf: Int,
    val startAt: ZonedDateTime,
    val endAt: ZonedDateTime?
) {
    fun toReservationEntity(): ReservationEntity {
        return ReservationEntity.create(
            customerId = customerId,
            restaurantId = restaurantId,
            aPartyOf = aPartyOf,
            startAt = startAt,
            endAt = endAt
        )
    }
}