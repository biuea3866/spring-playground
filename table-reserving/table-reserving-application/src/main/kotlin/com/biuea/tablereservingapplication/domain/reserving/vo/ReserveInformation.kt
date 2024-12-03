package com.biuea.tablereservingapplication.domain.reserving.vo

import com.biuea.tablereservingapplication.domain.reserving.aggregate.ReservingStatus
import java.time.ZonedDateTime

data class ReserveInformation(
    val status: ReservingStatus,
    val reservedAt: ZonedDateTime
) {
    init {
        require(this.status != ReservingStatus.RESERVED)
    }
}