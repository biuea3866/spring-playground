package com.biuea.tablereservingapplication.domain.reserving.vo

import com.biuea.tablereservingapplication.domain.reserving.aggregate.ReservingStatus
import java.time.ZonedDateTime

data class CancelInformation(
    val status: ReservingStatus,
    val cancelledAt: ZonedDateTime
) {
    init {
        require(this.status != ReservingStatus.CANCELLED)
    }
}