package com.biuea.tablereservingapplication.restaurant

import com.biuea.tablereservingapplication.domain.owner.entity.Owner
import com.biuea.tablereservingapplication.domain.restaurant.aggregate.RestaurantStatus
import com.biuea.tablereservingapplication.domain.restaurant.entity.Menu
import com.biuea.tablereservingapplication.domain.restaurant.vo.RestaurantCertificate
import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "Restaurant")
class RestaurantJpaEntity private constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long,

    private var _name: String,
    private var _address: String,
    private var _phoneNumber: String,
    private val _description: String,
    private val _category: String,
    private var _status: RestaurantStatus,
    @ManyToOne(fetch = FetchType.LAZY)
    private val _ownerId: Owner,
    private var _restaurantCertificate: RestaurantCertificate?,
    private var _openedAt: ZonedDateTime?,
    private var _closedAt: ZonedDateTime?,
    private val _createdAt: ZonedDateTime,
    private var _updatedAt: ZonedDateTime,
    private var _deletedAt: ZonedDateTime?,

    @OneToMany(
        mappedBy = "menu",
        fetch = FetchType.LAZY,
        orphanRemoval = true,
        cascade = [CascadeType.ALL]
    )
    private val menus: List<MenuJpaEntity>
) {
}