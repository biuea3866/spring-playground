package com.biuea.tablereservingapplication.domain.restaurant.event

import com.biuea.tablereservingapplication.core.DomainEventPayload
import com.biuea.tablereservingapplication.core.Id
import java.time.ZonedDateTime

data class OpenRestaurantEventPayload(
    val restaurantId: Id,
    val ownerId: Id,
    val openTime: ZonedDateTime
): DomainEventPayload