package com.biuea.tablereservingapplication.domain.restaurant.event

import com.biuea.tablereservingapplication.core.DomainEvent
import com.biuea.tablereservingapplication.core.Id
import java.time.ZonedDateTime

class CloseRestaurantEventPayload(
    val restaurantId: Id,
    val restaurantName: String,
    val closeTime: ZonedDateTime
)

data class CloseRestaurantEvent(
    override val occurredAt: ZonedDateTime,
    override val event: String,
    override val payload: CloseRestaurantEventPayload
): DomainEvent(
    occurredAt = occurredAt,
    event = event,
    payload = payload
)
