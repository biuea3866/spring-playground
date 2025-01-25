package com.biuea.reservation.domain.domain

import com.biuea.reservation.domain.repository.ReservationRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class ReservationDomainService(
    private val reservationRepository: ReservationRepository
) {
    @Transactional
    fun save(reservation: ReservationEntity): ReservationEntity {
        return reservationRepository.save(reservation)
    }

    @Transactional
    fun update(reservationId: Long, aPartyOf: Int, startAt: ZonedDateTime, endAt: ZonedDateTime?) {
        val reservation = reservationRepository.findBy(reservationId)
            ?: throw IllegalArgumentException("Reservation not found")
        reservation.update(
            aPartyOf = aPartyOf,
            startAt = startAt,
            endAt = endAt
        )
    }
}