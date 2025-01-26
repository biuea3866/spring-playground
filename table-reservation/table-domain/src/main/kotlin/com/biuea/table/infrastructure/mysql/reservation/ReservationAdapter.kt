package com.biuea.table.infrastructure.mysql.reservation

import com.biuea.table.domain.reservation.ReservationEntity
import com.biuea.table.domain.reservation.ReservationRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.ZonedDateTime

@Component
class ReservationAdapter(
    private val reservationRepository: ReservationJpaRepository
): ReservationRepository {
    override fun findBy(id: Long): ReservationEntity? {
        return reservationRepository.findByIdOrNull(id)
    }

    override fun save(reservation: ReservationEntity): ReservationEntity {
        return reservationRepository.save(reservation)
    }

    override fun findBy(ids: Set<Long>, entrance: Boolean, today: ZonedDateTime): Set<ReservationEntity> {
        return reservationRepository.findByIdInAndEntranceIsAndCreatedAtAfter(ids, entrance, today)
    }
}