package com.biuea.table.domain.reservation

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.ZonedDateTime

@Entity
@SQLDelete(sql = "UPDATE reservation SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@Table(name = "reservation")
class ReservationEntity(
    @Embedded
    var reservationInfo: ReservationInfo,

    @Embedded
    var timeSlot: TimeSlot,

    @Column(name = "customer_id")
    val customerId: Long,

    @Column(name = "restaurant_id")
    val restaurantId: Long,

    @Column(name = "entrance")
    val entrance: Boolean,

    @Column(name = "created_at")
    var createdAt: ZonedDateTime,

    @Column(name = "updated_at")
    var updatedAt: ZonedDateTime,

    @Column(name = "deleted_at")
    var deletedAt: ZonedDateTime?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    fun confirm(waitingNumber: Int) {
        this.reservationInfo.confirm(waitingNumber)
        this.updatedAt = ZonedDateTime.now()
    }

    fun cancel() {
        this.reservationInfo.cancel()
        this.updatedAt = ZonedDateTime.now()
    }

    fun decline() {
        this.reservationInfo.decline()
        this.updatedAt = ZonedDateTime.now()
    }

    fun update(
        aPartyOf: Int,
        startAt: ZonedDateTime,
        endAt: ZonedDateTime?
    ) {
        this.reservationInfo = this.reservationInfo.update(aPartyOf)
        this.timeSlot = this.timeSlot.update(startAt, endAt)
        this.updatedAt = ZonedDateTime.now()
    }

    companion object {
        fun create(
            customerId: Long,
            restaurantId: Long,
            aPartyOf: Int,
            startAt: ZonedDateTime,
            endAt: ZonedDateTime?
        ): ReservationEntity {
            return ReservationEntity(
                reservationInfo = ReservationInfo.create(
                    aPartyOf = aPartyOf,
                    reservationNumber = PENDING_NUMBER,
                    status = ReservationStatus.REQUESTED
                ),
                timeSlot = TimeSlot.create(
                    start = startAt,
                    end = endAt
                ),
                customerId = customerId,
                restaurantId = restaurantId,
                entrance = false,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now(),
                deletedAt = null
            )
        }

        const val PENDING_NUMBER = -1
    }
}

@Embeddable
data class ReservationInfo(
    @Column(name = "a_party_of")
    var aPartyOf: Int,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: ReservationStatus,

    @Column(name = "reservation_number")
    var reservationNumber: Int,

    @Column(name = "requested_at")
    var requestedAt: ZonedDateTime,

    @Column(name = "confirmed_at")
    var confirmedAt: ZonedDateTime?,

    @Column(name = "canceled_at")
    var canceledAt: ZonedDateTime?,

    @Column(name = "declined_at")
    var declinedAt: ZonedDateTime?
) {
    fun confirm(reservationNumber: Int) {
        check(this.status == ReservationStatus.RESERVED) { "Already Reserved" }
        check(this.status == ReservationStatus.DECLINED) { "Already Declined" }
        require(this.status == ReservationStatus.REQUESTED) { "Reserve " }

        this.status = ReservationStatus.RESERVED
        this.reservationNumber = reservationNumber
        this.confirmedAt = ZonedDateTime.now()
    }

    fun cancel() {
        this.status = ReservationStatus.CANCELED
        this.canceledAt = ZonedDateTime.now()
    }

    fun decline() {
        this.status = ReservationStatus.DECLINED
        this.declinedAt = ZonedDateTime.now()
    }

    fun update(aPartyOf: Int): ReservationInfo = this.copy(
        aPartyOf = aPartyOf
    )

    companion object {
        fun create(
            aPartyOf: Int,
            reservationNumber: Int,
            status: ReservationStatus,
        ): ReservationInfo {
            return ReservationInfo(
                aPartyOf = aPartyOf,
                reservationNumber = reservationNumber,
                status = status,
                requestedAt = ZonedDateTime.now(),
                confirmedAt = null,
                canceledAt = null,
                declinedAt = null
            )
        }
    }
}

@Embeddable
data class TimeSlot(
    var start: ZonedDateTime,
    var end: ZonedDateTime?
) {
    fun update(
        startAt: ZonedDateTime,
        endAt: ZonedDateTime?
    ): TimeSlot = this.copy(
        start = startAt,
        end = endAt
    )

    companion object {
        fun create(
            start: ZonedDateTime,
            end: ZonedDateTime?
        ): TimeSlot {
            return TimeSlot(
                start = start,
                end = end
            )
        }
    }
}

enum class ReservationStatus {
    REQUESTED,
    RESERVED,
    CANCELED,
    DECLINED
}

enum class CustomerType {
    USER,
    GUEST
}