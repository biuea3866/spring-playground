package com.biuea.reservation.mysql.reservation.jpa

import com.biuea.reservation.domain.domain.ReservationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationJpaRepository: JpaRepository<ReservationEntity, Long> {
}