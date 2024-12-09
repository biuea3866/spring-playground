package com.biuea.tablereservingapplication.infrastructure.mysql.restaurant.jpa

import com.biuea.tablereservingapplication.core.JpaEntity
import com.biuea.tablereservingapplication.domain.restaurant.aggregate.RestaurantAggregation
import com.biuea.tablereservingapplication.domain.restaurant.aggregate.RestaurantAggregation.Companion.of
import com.biuea.tablereservingapplication.domain.restaurant.aggregate.RestaurantStatus
import com.biuea.tablereservingapplication.domain.restaurant.vo.RestaurantCertificate
import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "Restaurant")
class RestaurantJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(
        name = "name",
        nullable = false
    )
    val name: String,
    @Column(
        name = "address",
        nullable = false
    )
    val address: String,
    @Column(
        name = "phone_number",
        nullable = false
    )
    val phoneNumber: String,
    @Column(
        name = "description",
        nullable = false
    )
    val description: String,
    @Column(
        name = "category",
        nullable = false
    )
    val category: String,
    @Column(
        name = "status",
        nullable = false
    )
    @Enumerated(EnumType.STRING)
    val status: RestaurantStatus,
    @Column(
        name = "owner_id",
        nullable = false
    )
    val ownerId: Long,
    @Embedded
    val restaurantCertificate: RestaurantCertificateJpaValueObject?,
    @Column(
        name = "opened_at",
        nullable = true
    )
    val openedAt: ZonedDateTime?,
    @Column(
        name = "closed_at",
        nullable = true
    )
    val closedAt: ZonedDateTime?,
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
    val deletedAt: ZonedDateTime?,

    @OneToMany(
        mappedBy = "menu",
        fetch = FetchType.LAZY,
        orphanRemoval = true,
        cascade = [CascadeType.ALL]
    )
    val menus: List<MenuJpaEntity>
): JpaEntity {
    fun toAggregate(): RestaurantAggregation {
        return this.of(
            id = this.id.convertLongToId(),
            name = this.name,
            address = this.address,
            phoneNumber = this.phoneNumber,
            description = this.description,
            category = this.category,
            status = this.status,
            ownerId = this.ownerId.convertLongToId(),
            restaurantCertificate = this.restaurantCertificate?.toDomain(),
            openedAt = this.openedAt,
            closedAt = this.closedAt,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            deletedAt = this.deletedAt,
            menus = this.menus.map { it.toDomain() }.toMutableList()
        )
    }
}

@Embeddable
class RestaurantCertificateJpaValueObject(
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
        name = "certificate_created_at",
        nullable = false
    )
    val createdAt: ZonedDateTime,
    @Column(
        name = "certificate_updated_at",
        nullable = false
    )
    val updatedAt: ZonedDateTime,
    @Column(
        name = "certificate_deleted_at",
        nullable = true
    )
    val deletedAt: ZonedDateTime?
) {
    fun toDomain(): RestaurantCertificate {
        return RestaurantCertificate.of(
            bucket = this.bucket,
            key = this.key,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            deletedAt = this.deletedAt
        )
    }
}