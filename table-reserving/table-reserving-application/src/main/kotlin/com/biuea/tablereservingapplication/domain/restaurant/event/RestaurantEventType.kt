package com.biuea.tablereservingapplication.domain.restaurant.event

import com.biuea.tablereservingapplication.core.DomainEventType

enum class RestaurantEventType: DomainEventType {
    OPEN,
    CLOSE
}