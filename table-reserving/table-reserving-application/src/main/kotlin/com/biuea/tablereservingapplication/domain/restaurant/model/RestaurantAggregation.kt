package com.biuea.tablereservingapplication.domain.restaurant.model

import com.biuea.tablereservingapplication.core.Aggregate
import com.biuea.tablereservingapplication.core.DomainEvent
import com.biuea.tablereservingapplication.core.Id
import com.biuea.tablereservingapplication.domain.restaurant.event.OpenRestaurantEventPayload
import com.biuea.tablereservingapplication.domain.restaurant.event.RestaurantEventType
import java.time.ZonedDateTime

/**
 * 식당 어그리거트
 */
class RestaurantAggregation private constructor(
    _id: Id,
    _name: String,
    _address: String,
    _phoneNumber: String,
    _description: String,
    _category: String,
    _status: RestaurantStatus,
    _ownerId: Id,
    _menus: MutableSet<Menu>,
    _openedAt: ZonedDateTime,
    _closedAt: ZonedDateTime,
    _createdAt: ZonedDateTime,
    _updatedAt: ZonedDateTime,
    _deletedAt: ZonedDateTime?
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
    var status: RestaurantStatus = _status
        private set
    var ownerId: Id = _ownerId
        private set
    var menus: MutableSet<Menu> = _menus
        private set
    var openedAt: ZonedDateTime = _openedAt
        private set
    var createdAt: ZonedDateTime = _createdAt
        private set
    var updatedAt: ZonedDateTime = _updatedAt
        private set
    var deletedAt: ZonedDateTime? = _deletedAt
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
            status: RestaurantStatus,
            ownerId: Id,
            menus: MutableSet<Menu>,
            openedAt: ZonedDateTime,
            closedAt: ZonedDateTime,
            createdAt: ZonedDateTime,
            updatedAt: ZonedDateTime,
            deletedAt: ZonedDateTime?
        ): RestaurantAggregation {
            return RestaurantAggregation(
                _id = id,
                _name = name,
                _address = address,
                _phoneNumber = phoneNumber,
                _description = description,
                _category = category,
                _status = status,
                _ownerId = ownerId,
                _menus = menus,
                _openedAt = openedAt,
                _closedAt = closedAt,
                _createdAt = createdAt,
                _updatedAt = updatedAt,
                _deletedAt = deletedAt
            )
        }
    }
}

enum class RestaurantStatus {
    PENDING,
    OPEN,
    CLOSED
}