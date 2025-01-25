package com.biuea.table.adapter.webclient

import com.biuea.reservation.application.port.out.ReservationOutputPort
import com.biuea.reservation.domain.domain.ReservationEntity
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.time.ZonedDateTime

@Component
class ReservationAdapter(
    private val webClient: WebClient
): ReservationOutputPort {
    override fun save(reservation: ReservationEntity): ReservationEntity {
        TODO("Not yet implemented")
    }

    override fun update(reservationId: Long, aPartyOf: Int, startAt: ZonedDateTime, endAt: ZonedDateTime?) {
        TODO("Not yet implemented")
    }
}