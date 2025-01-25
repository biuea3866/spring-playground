package com.biuea.reservation.application.port.`in`

import com.biuea.reservation.application.port.out.ReservationOutputPort
import com.biuea.reservation.application.port.out.RestaurantOutputPort
import com.biuea.reservation.application.usecase.ReservationRequestUseCase
import com.biuea.reservation.application.usecase.UpdateReservationUseCase
import org.springframework.stereotype.Service

@Service
class ReserveInputPort(
    private val restaurantOutputPort: RestaurantOutputPort,
    private val reservationOutputPort: ReservationOutputPort
): ReservationRequestUseCase, UpdateReservationUseCase {
    override fun execute(command: ReservationRequestUseCase.ReservationRequest) {
        val calculateReservationCount = restaurantOutputPort.calculateReservationCount(command.restaurantId)
        reservationOutputPort.save(command.toReservationEntity(calculateReservationCount))
    }

    override fun execute(command: UpdateReservationUseCase.UpdateReservationRequest) {
        reservationOutputPort.update(
            reservationId = command.reservationId,
            aPartyOf = command.aPartyOf,
            startAt = command.startAt,
            endAt = command.endAt
        )
    }
}