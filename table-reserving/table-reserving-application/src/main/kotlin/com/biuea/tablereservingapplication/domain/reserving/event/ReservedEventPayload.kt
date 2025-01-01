package com.biuea.tablereservingapplication.domain.reserving.event

import com.biuea.tablereservingapplication.core.Id
import java.time.ZonedDateTime

class ReservedEventPayload(
    val reservingId: Id,
    val nickname: String,
    val restaurantName: String,
    val menuNames: List<String>,
    val reservedAt: ZonedDateTime
)