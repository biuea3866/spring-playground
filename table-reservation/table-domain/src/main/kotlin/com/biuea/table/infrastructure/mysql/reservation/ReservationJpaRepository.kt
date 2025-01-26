package com.biuea.table.infrastructure.mysql.reservation

import com.biuea.table.domain.reservation.ReservationEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.time.ZonedDateTime

interface ReservationJpaRepository: JpaRepository<ReservationEntity, Long> {
    fun findByIdInAndEntranceIsAndCreatedAtAfter(
        ids: Set<Long>,
        entrance: Boolean,
        today: ZonedDateTime
    ): Set<ReservationEntity>
}