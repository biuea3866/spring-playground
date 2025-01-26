package com.biuea.table.domain.reservation

import org.springframework.stereotype.Service

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository
) {
    fun updateReservation(
        waitingNumber: Int,
        reservation: ReservationEntity
    ) {

    }
}