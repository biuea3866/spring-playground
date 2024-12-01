package com.biuea.tablereservingapplication.domain.restaurant.event

import com.biuea.tablereservingapplication.core.DomainEventPayload
import com.biuea.tablereservingapplication.core.Id

class DeleteRestaurantPayload(
    val restaurantId: Id
): DomainEventPayload {
}