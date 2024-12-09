package com.biuea.tablereservingapplication.infrastructure.mysql.restaurant.jpa

import com.biuea.tablereservingapplication.core.JpaEntity
import com.biuea.tablereservingapplication.domain.restaurant.entity.Menu
import com.biuea.tablereservingapplication.domain.restaurant.entity.Menu.Companion.of
import com.biuea.tablereservingapplication.domain.restaurant.vo.MenuImage
import com.biuea.tablereservingapplication.domain.restaurant.vo.MenuImage.Companion.of
import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "menu")
class MenuJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(
        name = "restaurant_id",
        nullable = false
    )
    val restaurantId: Long,
    @Column(
        name = "name",
        nullable = false
    )
    val name: String,
    @Column(
        name = "price",
        nullable = false
    )
    val price: Int,
    @Column(
        name = "description",
        nullable = false
    )
    val description: String,
    @OneToMany(
        mappedBy = "menu_image",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    val images: List<MenuImageEntity>,
    @Column(
        name = "created_at",
        nullable = false
    )
    val createdAt: ZonedDateTime,
    @Column(
        name = "updated_at",
        nullable = false
    )
    val updatedAt: ZonedDateTime,
    @Column(
        name = "deleted_at",
        nullable = true
    )
    val deletedAt: ZonedDateTime?
): JpaEntity {
}

@Entity
@Table(name = "menu_image")
class MenuImageEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(
        name = "bucket",
        nullable = false
    )
    val bucket: String,
    @Column(
        name = "key",
        nullable = false
    )
    val key: String,
    @Column(
        name = "filename",
        nullable = false
    )
    val filename: String,
    @Column(
        name = "created_at",
        nullable = false
    )
    val createdAt: ZonedDateTime,
    @Column(
        name = "updated_at",
        nullable = false
    )
    val updatedAt: ZonedDateTime,
    @Column(
        name = "deleted_at",
        nullable = true
    )
    val deletedAt: ZonedDateTime?
): JpaEntity

fun MenuJpaEntity.toDomain(): Menu {
    return this.of(
        restaurantId = this.restaurantId.convertLongToId(),
        name = this.name,
        price = this.price,
        description = this.description,
        menuImages = this.images.map { it.toDomain() }.toMutableList(),
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        deletedAt = this.deletedAt
    )
}

fun MenuImageEntity.toDomain(): MenuImage {
    return this.of(
        bucket = this.bucket,
        key = this.key,
        filename = this.filename,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        deletedAt = this.deletedAt
    )
}