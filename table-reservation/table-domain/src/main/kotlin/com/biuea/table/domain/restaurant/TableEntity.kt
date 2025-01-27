package com.biuea.table.domain.restaurant

import jakarta.persistence.*

@Table(name = "table")
@Entity
class TableEntity(
    @Column(name = "table_number")
    val tableNumber: Int,

    @Column(name = "available")
    var available: Boolean,

    @Column(name = "status")
    val status: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    val restaurant: RestaurantEntity? = null

    fun reserve() {
        this.available = false
    }

    fun occupy() {
        this.available = true
    }
}