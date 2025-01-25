package com.biuea.reservation.application.port.out

import com.biuea.reservation.domain.domain.ReservationEntity
import java.time.ZonedDateTime

interface ReservationOutputPort {
    fun save(reservation: ReservationEntity): ReservationEntity
    fun update(
        reservationId: Long,
        aPartyOf: Int,
        startAt: ZonedDateTime,
        endAt: ZonedDateTime?
    )
}