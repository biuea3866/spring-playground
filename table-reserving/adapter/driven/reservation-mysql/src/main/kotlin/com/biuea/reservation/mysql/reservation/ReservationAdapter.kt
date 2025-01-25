package com.biuea.reservation.mysql.reservation

import com.biuea.reservation.application.port.out.ReservationOutputPort
import com.biuea.reservation.domain.domain.ReservationEntity
import com.biuea.reservation.domain.repository.ReservationRepository
import com.biuea.reservation.mysql.reservation.jpa.ReservationJpaRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class ReservationAdapter(
    private val reservationJpaRepository: ReservationJpaRepository
): ReservationRepository {
    override fun findBy(reservationId: Long): ReservationEntity? {
        return reservationJpaRepository.findByIdOrNull(reservationId)
    }

    override fun save(reservation: ReservationEntity): ReservationEntity {
        return reservationJpaRepository.save(reservation)
    }
}