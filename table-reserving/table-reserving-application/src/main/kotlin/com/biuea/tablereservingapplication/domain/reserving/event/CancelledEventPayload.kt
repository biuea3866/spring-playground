package com.biuea.tablereservingapplication.domain.reserving.event

import com.biuea.tablereservingapplication.core.DomainEventPayload

class CancelledEventPayload(
    val userId: String,
    val restaurantId: String,
    val restaurantName: String,
    val menuNames: List<String>
): DomainEventPayload {
}