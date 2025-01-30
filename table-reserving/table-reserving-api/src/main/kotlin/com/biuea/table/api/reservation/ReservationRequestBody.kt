package com.biuea.table.api.reservation

import com.biuea.table.application.ReservationRequest
import java.time.ZonedDateTime

data class ReservationRequestBody(
    val customerId: Long,
    val restaurantId: Long,
    val aPartyOf: Int,
    val startAt: ZonedDateTime,
    val endAt: ZonedDateTime?
) {
    fun toReservationRequest(): ReservationRequest {
        return ReservationRequest(
            customerId = customerId,
            restaurantId = restaurantId,
            aPartyOf = aPartyOf,
            startAt = startAt,
            endAt = endAt
        )
    }
}