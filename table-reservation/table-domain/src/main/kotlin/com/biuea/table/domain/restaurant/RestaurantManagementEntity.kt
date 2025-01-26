package com.biuea.table.domain.restaurant

import jakarta.persistence.*
import java.time.ZonedDateTime

@Table(name = "restaurant_management")
@Entity
class RestaurantManagementEntity(
    @Column(name = "open_time")
    val openTime: ZonedDateTime,

    @Column(name = "auto_confirm")
    val autoConfirm: Boolean,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    val restaurant: RestaurantEntity? = null
}