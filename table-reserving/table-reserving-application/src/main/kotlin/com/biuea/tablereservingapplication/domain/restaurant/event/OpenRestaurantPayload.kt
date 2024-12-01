package com.biuea.tablereservingapplication.domain.restaurant.event

import com.biuea.tablereservingapplication.core.DomainEvent
import com.biuea.tablereservingapplication.core.DomainEventType
import com.biuea.tablereservingapplication.core.Id
import java.time.ZonedDateTime

class OpenRestaurantEventPayload(
    val restaurantId: Id,
    val ownerId: Id,
    val openTime: ZonedDateTime
)

data class OpenRestaurantEvent(
    override val occurredAt: ZonedDateTime,
    override val event: String,
    override val payload: OpenRestaurantEventPayload
): DomainEvent(
    occurredAt = occurredAt,
    event = event,
    payload = payload,
    domainEventType = DomainEventType.OPEN_RESTAURANT
) {
    companion object {
        fun from(
            event: String,
            payload: OpenRestaurantEventPayload
        ): OpenRestaurantEvent {
            return OpenRestaurantEvent(
                event = event,
                payload = payload,
                occurredAt = ZonedDateTime.now()
            )
        }
    }
}
