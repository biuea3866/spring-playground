package com.biuea.tablereservingapplication.domain.reserving.aggregate

import com.biuea.tablereservingapplication.core.Id
import com.biuea.tablereservingapplication.domain.reserving.vo.CancelInformation
import com.biuea.tablereservingapplication.domain.reserving.vo.MenuInfo
import com.biuea.tablereservingapplication.domain.reserving.vo.ReserveInformation
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
    val cancelInformation: CancelInformation? get() = when (this._status == ReservingStatus.CANCELLED) {
        true -> CancelInformation(
            status = this._status,
            cancelledAt = this._cancelledAt ?: ZonedDateTime.now()
        )
        false -> null
    }
    val reservingInformation: ReserveInformation? get() = when (this._status == ReservingStatus.RESERVED) {
        true -> ReserveInformation(
            status = this._status,
            reservedAt = this._reservedAt ?: ZonedDateTime.now()
        )
        false -> null
    }

//    fun reserve(
//        publish: (DomainEvent) -> Unit
//    ): ReservingAggregate {
//        require(this.status in ReservingStatus.availableReserving())
//
//        this._reservedAt = ZonedDateTime.now()
//        this._status = ReservingStatus.RESERVED
//        this._updatedAt = ZonedDateTime.now()
//
//        publish(
//            ReservedEvent(
//                event = "event.reserving.reserved",
//                payload = payload
//            )
//        )
//
//        return this
//    }
//
//    fun cancel(
//        publish: (DomainEvent) -> Unit,
//        payload: DomainEventPayload
//    ): ReservingAggregate {
//        require(payload is CancelledEventPayload) { "Payload type mismatch" }
//
//        this._status = ReservingStatus.CANCELLED
//        this._updatedAt = ZonedDateTime.now()
//
//        publish(
//            DomainEvent.build(
//                event = "event.reserving.cancelled",
//                payload = payload
//            )
//        )
//
//        return this
//    }
//
//    fun entrance(
//        publish: (DomainEvent) -> Unit,
//        payload: DomainEventPayload
//    ): ReservingAggregate {
//        require(payload is ReservedEventPayload) { "Payload type mismatch" }
//
//        this._status = ReservingStatus.RESERVED
//        this._updatedAt = ZonedDateTime.now()
//
//        publish(
//            DomainEvent.build(
//                event = "event.reserving.entrance",
//                payload = payload
//            )
//        )
//
//        return this
//    }
//
//    fun exit(
//        publish: (DomainEvent) -> Unit,
//        payload: DomainEventPayload
//    ): ReservingAggregate {
//        require(payload is ReservedEventPayload) { "Payload type mismatch" }
//
//        this._status = ReservingStatus.RESERVED
//        this._updatedAt = ZonedDateTime.now()
//
//        publish(
//            DomainEvent.build(
//                event = "event.reserving.exit",
//                payload = payload
//            )
//        )
//
//        return this
//    }

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
        ): ReservingAggregate {
            return ReservingAggregate(
                _id = Id(0L),
                _restaurantId = restaurantId,
                _userId = userId,
                _status = ReservingStatus.PENDING,
                _menuInfos = menuInfos.toMutableList(),
                _reservedAt = null,
                _cancelledAt = null,
                _createdAt = ZonedDateTime.now(),
                _updatedAt = ZonedDateTime.now(),
                _deletedAt = null
            )
        }
    }
}