package com.biuea.tablereservingapplication.domain.restaurant.model

import com.biuea.tablereservingapplication.core.Aggregate
import com.biuea.tablereservingapplication.core.DomainEvent
import com.biuea.tablereservingapplication.core.Id
import com.biuea.tablereservingapplication.domain.restaurant.event.OpenRestaurantEventPayload
import com.biuea.tablereservingapplication.domain.restaurant.event.RestaurantEventType
import java.time.ZonedDateTime

class RestaurantAggregation private constructor(
    _id: Id,
    _name: String,
    _address: String,
    _phoneNumber: String,
    _description: String,
    _category: String,
    _ownerId: Id,
    _menus: MutableSet<Menu>,
    _openedAt: ZonedDateTime
): Aggregate() {
    var id: Id = _id
        private set
    var name: String = _name
        private set
    var address: String = _address
        private set
    var phoneNumber: String = _phoneNumber
        private set
    var description: String = _description
        private set
    var category: String = _category
        private set
    var ownerId: Id = _ownerId
        private set
    var menus: MutableSet<Menu> = _menus
        private set
    var openedAt: ZonedDateTime = _openedAt
        private set

    fun openRestaurant(
        publish:(DomainEvent) -> Unit
    ): RestaurantAggregation {
        this.openedAt = ZonedDateTime.now()

        val event = DomainEvent.build(
            payload = OpenRestaurantEventPayload(
                restaurantId = id,
                ownerId = ownerId,
                openTime = ZonedDateTime.now()
            ),
            eventType = RestaurantEventType.OPEN,
            event = "event.restaurant.opened"
        )

        publish(event)

        return this
    }

    fun openRestaurantNotEventPublishing(): RestaurantAggregation {
        this.openedAt = ZonedDateTime.now()

        return this
    }

    companion object {
        fun build(
            id: Id,
            name: String,
            address: String,
            phoneNumber: String,
            description: String,
            category: String,
            ownerId: Id,
            menus: MutableSet<Menu>,
            openedAt: ZonedDateTime
        ): RestaurantAggregation {
            return RestaurantAggregation(
                _id = id,
                _name = name,
                _address = address,
                _phoneNumber = phoneNumber,
                _description = description,
                _category = category,
                _ownerId = ownerId,
                _menus = menus,
                _openedAt = openedAt
            )
        }
    }
}