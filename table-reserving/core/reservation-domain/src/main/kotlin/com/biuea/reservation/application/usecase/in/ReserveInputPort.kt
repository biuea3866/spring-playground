package com.biuea.reservation.application.usecase.`in`

import com.biuea.reservation.application.usecase.ReservationRequestUseCase
import org.springframework.stereotype.Service

@Service
class ReserveInputPort(
    private val reservationOutputPort: ReservationOutputPort
): ReservationRequestUseCase {
    override fun execute(command: ReservationRequestUseCase.ReservationRequest) {
        reservationOutputPort.save(command.toReservationEntity())
    }
}