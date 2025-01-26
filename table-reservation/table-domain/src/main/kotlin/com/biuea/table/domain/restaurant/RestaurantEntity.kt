package com.biuea.table.domain.restaurant

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
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
class RestaurantEntity {
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

    fun isRemainTable(): Boolean {
        return this.tables.any { it.available }
    }

    fun occupyTable() {
        this.tables.firstOrNull { it.available }
            ?.occupy()
            ?: throw IllegalArgumentException("No table available")
    }

    private fun todayReservationManagement(): RestaurantManagementEntity {
        return this.restaurantManagements.firstOrNull { it.openTime.toLocalDate() == ZonedDateTime.now().toLocalDate() }
            ?: throw IllegalArgumentException("No restaurant management today")
    }

    fun isTurnedOnAutoConfirm(): Boolean {
        return this.todayReservationManagement().autoConfirm
    }
}

@Table(name = "reservationRelation")
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