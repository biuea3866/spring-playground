package com.biuea.table.domain.restaurant

import com.biuea.table.application.support.ReservationCoordinator
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.ZonedDateTime

@Table(name = "restaurant")
@Entity
class RestaurantEntity(
    @Column(name = "name")
    val name: String,

    @Embedded
    val addressInfo: AddressInfo,

    @Embedded
    val registrationInfo: RegistrationInfo?,

    @Embedded
    val salesInfo: SalesInfo,

    @Column(name = "contact")
    val contact: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "status")
    val status: String,

    @Column(name = "opened_at")
    val openedAt: ZonedDateTime,

    @Column(name = "closed_at")
    val closedAt: ZonedDateTime
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @OneToMany(
        mappedBy = "restaurant",
        fetch = FetchType.LAZY,
        orphanRemoval = true,
        cascade = [CascadeType.ALL]
    )
    val restaurantManagements: Set<RestaurantManagementEntity> = setOf()

    @OneToMany(
        mappedBy = "restaurant",
        fetch = FetchType.LAZY,
        orphanRemoval = true,
        cascade = [CascadeType.ALL]
    )
    val tables: Set<TableEntity> = setOf()

    @OneToMany(
        mappedBy = "restaurant",
        fetch = FetchType.LAZY,
        orphanRemoval = true,
        cascade = [CascadeType.ALL]
    )
    val reservationRelations: Set<ReservationRelation> = setOf()

    @Transient
    lateinit var waitingNumberStrategy: WaitingNumberStrategy

    fun changeWaitingNumberStrategy(waitingNumberStrategy: WaitingNumberStrategy) {
        this.waitingNumberStrategy = waitingNumberStrategy
    }

    fun isRemainTable(): Boolean {
        return this.tables.any { it.available }
    }

    private fun occupyTable() {
        if (this.isRemainTable()) {
            this.tables.firstOrNull { it.available }
                ?.occupy()
                ?: throw IllegalArgumentException("No table available")
        }
    }

    private fun todayReservationManagement(): RestaurantManagementEntity {
        return this.restaurantManagements.firstOrNull { it.openTime.toLocalDate() == ZonedDateTime.now().toLocalDate() }
            ?: throw IllegalArgumentException("No restaurant management today")
    }

    fun isTurnedOnAutoConfirm(): Boolean {
        return this.todayReservationManagement().autoConfirm
    }

    fun issueWaitingNumber(): Int {
        if (this.waitingNumberStrategy is ConfirmWaitingNumberStrategy) this.occupyTable()

        return this.waitingNumberStrategy.issue()
    }
}

@Embeddable
data class AddressInfo(
    // 서울특별시, 대구광역시 ..
    @Column(name = "province")
    val province: String,

    // 강남구, 중구 ..
    @Column(name = "municipality")
    val municipality: String,

    // 역삼동, 동대구역 ..
    @Column(name = "city_district")
    val cityDistrict: String,

    // 123-456, 789-123 ..
    @Column(name = "lot_number")
    val lotNumber: String,

    // 주소
    @Column(name = "address")
    val address: String,
)

@Embeddable
data class RegistrationInfo(
    @Column(name = "registration_number")
    val registrationNumber: String,
    @Column(name = "s3_key")
    val s3Key: String,
    @Column(name = "s3_bucket")
    val s3Bucket: String,
    @Column(name = "filename")
    val filename: String
)

@Embeddable
data class SalesInfo(
    @Column(name = "opening_time")
    val openingTime: ZonedDateTime,

    @Column(name = "closing_time")
    val closingTime: ZonedDateTime,

    @Column(name = "start_break_time")
    val startBreakTime: ZonedDateTime?,

    @Column(name = "end_break_time")
    val endBreakTime: ZonedDateTime?,

    @Column(name = "closed_days")
    val closedDays: String
)

@Table(name = "restaurant_reservation_relation")
@Entity
class ReservationRelation(
    @Column(name = "reservation_id")
    val reservationId: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    val restaurant: RestaurantEntity? = null
}