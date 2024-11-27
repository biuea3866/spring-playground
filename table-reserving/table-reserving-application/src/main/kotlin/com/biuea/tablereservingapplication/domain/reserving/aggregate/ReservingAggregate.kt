package com.biuea.tablereservingapplication.domain.reserving.domain

import com.biuea.tablereservingapplication.core.DomainEvent
import com.biuea.tablereservingapplication.core.Id
import com.biuea.tablereservingapplication.domain.reserving.event.CancelledEventPayload
import com.biuea.tablereservingapplication.domain.reserving.event.ReservedEventPayload
import java.time.ZonedDateTime

enum class ReservingStatus {
    PENDING,
    RESERVED,
    CANCELLED
}

/**
 * 예약 어그리거트
 */
class ReservingAggregate private constructor(
    _id: Id,
    _restaurantId: Id,
    _userId: Id,
    _status: ReservingStatus,
    _reservedAt: ZonedDateTime?,
    _cancelledAt: ZonedDateTime?,
    _createdAt: ZonedDateTime,
    _updatedAt: ZonedDateTime,
    _deletedAt: ZonedDateTime?
) {
    var id: Id = _id
        private set
    var restaurantId: Id = _restaurantId
        private set
    var userId: Id = _userId
        private set
    var status: ReservingStatus = _status
        private set
    var reservedAt: ZonedDateTime? = _reservedAt
        private set
    var cancelledAt: ZonedDateTime? = _cancelledAt
        private set
    var createdAt: ZonedDateTime = _createdAt
        private set
    var updatedAt: ZonedDateTime = _updatedAt
        private set
    var deletedAt: ZonedDateTime? = _deletedAt
        private set

    fun reserve(
        publish: (DomainEvent) -> Unit,
        nickname: String,
        restaurantName: String,
        menuNames: List<String>
    ): ReservingAggregate {
        this.reservedAt = ZonedDateTime.now()
        this.status = ReservingStatus.RESERVED

        publish(
            DomainEvent.build(
                event = "event.reserving.reserved",
                payload = ReservedEventPayload(
                    reservingId = this.id,
                    nickname = nickname,
                    restaurantName = restaurantName,
                    menuNames = menuNames,
                    reservedAt = this.reservedAt!!
                )
            )
        )

        return this
    }

    fun cancel(
        publish: (DomainEvent) -> Unit,
        payload: CancelledEventPayload
    ): ReservingAggregate {
        this.status = ReservingStatus.CANCELLED

        publish(
            DomainEvent.build(
                event = "event.reserving.cancelled",
                payload = CancelledEventPayload(
                    userId =
                )
            )
        )

        return this
    }

    companion object {
        fun create(
            restaurantId: Id,
            userId: Id
        ): ReservingAggregate {
            return ReservingAggregate(
                _id = Id(0L),
                _restaurantId = restaurantId,
                _userId = userId,
                _status = ReservingStatus.PENDING,
                _reservedAt = null,
                _cancelledAt = null,
                _createdAt = ZonedDateTime.now(),
                _updatedAt = ZonedDateTime.now(),
                _deletedAt = null
            )
        }
    }
}