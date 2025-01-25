package com.biuea.reservation.application.usecase

import java.time.ZonedDateTime

interface UpdateReservationUseCase {
    fun execute(command: UpdateReservationRequest)

    data class UpdateReservationRequest(
        val reservationId: Long,
        val aPartyOf: Int,
        val startAt: ZonedDateTime,
        val endAt: ZonedDateTime
    )
}