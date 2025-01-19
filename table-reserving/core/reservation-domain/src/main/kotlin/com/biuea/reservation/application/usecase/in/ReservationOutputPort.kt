package com.biuea.reservation.application.usecase.`in`

import com.biuea.reservation.domain.ReservationEntity

interface ReservationOutputPort {
    fun save(reservation: ReservationEntity): ReservationEntity
}