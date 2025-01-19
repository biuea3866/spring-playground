package com.biuea.reservation.domain

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.springframework.data.annotation.Transient
import java.time.ZonedDateTime

@Entity
@SQLDelete(sql = "UPDATE reservation SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@Table(name = "reservation")
class ReservationEntity(
    @Embedded
    var reservationInfo: ReservationInfo,

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

    fun confirm() {
        this.reservationInfo.confirm()
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

    companion object {
        fun request(
            customerId: Long,
            customerType: CustomerType,
            restaurantId: Long,
            restaurantType: RestaurantType,
            customerName: String,
            restaurantName: String,
            aPartyOf: Int
        ): ReservationEntity {
            return ReservationEntity(
                reservationInfo = ReservationInfo.request(
                    customerId = customerId,
                    customerType = customerType,
                    customerName = customerName,
                    restaurantId = restaurantId,
                    restaurantType = restaurantType,
                    restaurantName = restaurantName,
                    aPartyOf = aPartyOf
                ),
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now(),
                deletedAt = null
            )
        }
    }
}

@Embeddable
data class ReservationInfo(
    @Embedded
    var customer: Customer,

    @Embedded
    var restaurant: Restaurant,

    @Column(name = "a_party_of")
    var aPartyOf: Int,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: ReservationStatus,

    @Column(name = "requested_at")
    var requestedAt: ZonedDateTime,

    @Column(name = "confirmed_at")
    var confirmedAt: ZonedDateTime?,

    @Column(name = "canceled_at")
    var canceledAt: ZonedDateTime?,

    @Column(name = "declined_at")
    var declinedAt: ZonedDateTime?
) {
    fun confirm() {
        check(this.status == ReservationStatus.RESERVED) { "Already Reserved" }
        check(this.status == ReservationStatus.DECLINED) { "Already Declined" }
        require(this.status == ReservationStatus.REQUESTED) { "Reserve " }

        this.status = ReservationStatus.RESERVED
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

    companion object {
        fun request(
            customerId: Long,
            customerType: CustomerType,
            customerName: String,
            restaurantId: Long,
            restaurantName: String,
            restaurantType: RestaurantType,
            aPartyOf: Int
        ): ReservationInfo {
            return ReservationInfo(
                customer = Customer.create(
                    id = customerId,
                    name = customerName,
                    type = customerType
                ),
                restaurant = Restaurant.create(
                    id = restaurantId,
                    name = restaurantName,
                    type = restaurantType
                ),
                aPartyOf = aPartyOf,
                status = ReservationStatus.REQUESTED,
                requestedAt = ZonedDateTime.now(),
                confirmedAt = null,
                canceledAt = null,
                declinedAt = null
            )
        }
    }
}

@Embeddable
data class Customer(
    @Column(name = "customer_id")
    val id: Long,
    @Column(name = "customer_name")
    val name: String,
    @Column(name = "customer_type")
    @Enumerated(EnumType.STRING)
    val type: CustomerType
) {
    companion object {
        fun create(
            id: Long,
            name: String,
            type: CustomerType
        ): Customer {
            return Customer(
                id = id,
                name = name,
                type = type
            )
        }
    }
}

@Embeddable
data class Restaurant(
    @Column(name = "restaurant_id")
    val id: Long,
    @Column(name = "restaurant_name")
    val name: String,
    @Column(name = "restaurant_type")
    val type: RestaurantType
) {
    companion object {
        fun create(
            id: Long,
            name: String,
            type: RestaurantType
        ): Restaurant {
            return Restaurant(
                id = id,
                name = name,
                type = type
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

enum class RestaurantType {
    REGISTERED,
    UNREGISTERED
}