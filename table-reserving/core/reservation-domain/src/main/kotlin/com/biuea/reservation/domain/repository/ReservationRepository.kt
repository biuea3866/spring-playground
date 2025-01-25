package com.biuea.reservation.domain.repository

import com.biuea.reservation.domain.domain.ReservationEntity
import java.time.ZonedDateTime

interface ReservationRepository {
    fun findBy(reservationId: Long): ReservationEntity?
    fun save(reservation: ReservationEntity): ReservationEntity
}