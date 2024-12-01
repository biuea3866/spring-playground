package com.biuea.tablereservingapplication.domain.reserving.aggregate

import com.biuea.tablereservingapplication.core.DomainEvent
import com.biuea.tablereservingapplication.core.DomainEventPayload
import com.biuea.tablereservingapplication.core.Id
import com.biuea.tablereservingapplication.domain.reserving.event.CancelledEventPayload
import com.biuea.tablereservingapplication.domain.reserving.event.ReservedEventPayload
import com.biuea.tablereservingapplication.domain.reserving.vo.MenuInfo
import java.time.ZonedDateTime

enum class ReservingStatus {
    PENDING,
    RESERVED,
    CANCELLED,
    ENTERED,
    EXITED
    ;

    companion object {
        fun availableEntranceStatus(): List<ReservingStatus> {
            return listOf(PENDING)
        }

        fun availableReserving(): List<ReservingStatus> = listOf(
            PENDING
        )
    }
}

/**
 * 예약 어그리거트
 */
class ReservingAggregate private constructor(
    private val _id: Id,
    private val _restaurantId: Id,
    private val _userId: Id,
    private var _status: ReservingStatus,
    private val _menuInfos: MutableList<MenuInfo>,
    private val _blackListUsers: MutableList<BlackListUser>,
    private var _reservedAt: ZonedDateTime?,
    private var _cancelledAt: ZonedDateTime?,
    private val _createdAt: ZonedDateTime,
    private var _updatedAt: ZonedDateTime,
    private val _deletedAt: ZonedDateTime?
) {
    val id: Id get() = this._id
    val restaurantId: Id get() = this._restaurantId
    val userId: Id get() = this._userId
    val status: ReservingStatus get() = this._status
    val menuInfos: List<MenuInfo> get() = this._menuInfos
    val blackListUsers: List<BlackListUser> get() = this._blackListUsers
    val reservedAt: ZonedDateTime? get() = this._reservedAt
    val cancelledAt: ZonedDateTime? get() = this._cancelledAt
    val createdAt: ZonedDateTime get() = this._createdAt
    val updatedAt: ZonedDateTime get() = this._updatedAt
    val deletedAt: ZonedDateTime? get() = this._deletedAt

    fun reserve(
        publish: (DomainEvent) -> Unit,
        payload: DomainEventPayload
    ): ReservingAggregate {
        require(this.status in ReservingStatus.availableReserving())
        require(payload is ReservedEventPayload) { "Payload type mismatch" }

        this._reservedAt = ZonedDateTime.now()
        this._status = ReservingStatus.RESERVED
        this._updatedAt = ZonedDateTime.now()

        publish(
            DomainEvent.build(
                event = "event.reserving.reserved",
                payload = payload
            )
        )

        return this
    }

    fun cancel(
        publish: (DomainEvent) -> Unit,
        payload: DomainEventPayload
    ): ReservingAggregate {
        require(payload is CancelledEventPayload) { "Payload type mismatch" }

        this._status = ReservingStatus.CANCELLED
        this._updatedAt = ZonedDateTime.now()

        publish(
            DomainEvent.build(
                event = "event.reserving.cancelled",
                payload = payload
            )
        )

        return this
    }

    fun entrance(
        publish: (DomainEvent) -> Unit,
        payload: DomainEventPayload
    ): ReservingAggregate {
        require(payload is ReservedEventPayload) { "Payload type mismatch" }

        this._status = ReservingStatus.RESERVED
        this._updatedAt = ZonedDateTime.now()

        publish(
            DomainEvent.build(
                event = "event.reserving.entrance",
                payload = payload
            )
        )

        return this
    }

    fun exit(
        publish: (DomainEvent) -> Unit,
        payload: DomainEventPayload
    ): ReservingAggregate {
        require(payload is ReservedEventPayload) { "Payload type mismatch" }

        this._status = ReservingStatus.RESERVED
        this._updatedAt = ZonedDateTime.now()

        publish(
            DomainEvent.build(
                event = "event.reserving.exit",
                payload = payload
            )
        )

        return this
    }

    fun addMenu(menu: MenuInfo): ReservingAggregate {
        this._menuInfos.add(menu)
        this._updatedAt = ZonedDateTime.now()

        return this
    }

    fun removeMenu(menu: MenuInfo): ReservingAggregate {
        this._menuInfos.remove(menu)
        this._updatedAt = ZonedDateTime.now()

        return this
    }

    companion object {
        fun create(
            restaurantId: Id,
            userId: Id,
            menuInfos: List<MenuInfo>,
            blackListUsers: List<BlackListUser>
        ): ReservingAggregate {
            return ReservingAggregate(
                _id = Id(0L),
                _restaurantId = restaurantId,
                _userId = userId,
                _status = ReservingStatus.PENDING,
                _menuInfos = menuInfos,
                _blackListUsers = blackListUsers,
                _reservedAt = null,
                _cancelledAt = null,
                _createdAt = ZonedDateTime.now(),
                _updatedAt = ZonedDateTime.now(),
                _deletedAt = null
            )
        }
    }
}