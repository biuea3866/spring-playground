package com.biuea.table.domain.reservation

import java.time.LocalDate
import java.time.ZonedDateTime

interface ReservationRepository {
    fun findBy(id: Long): ReservationEntity?
    fun save(reservation: ReservationEntity): ReservationEntity
    fun findBy(
        ids: Set<Long>,
        entrance: Boolean,
        today: ZonedDateTime = ZonedDateTime.now().toLocalDate().atStartOfDay(ZonedDateTime.now().zone)
    ): Set<ReservationEntity>
}