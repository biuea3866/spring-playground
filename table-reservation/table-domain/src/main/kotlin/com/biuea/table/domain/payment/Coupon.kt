package com.biuea.table.domain.payment

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.ZonedDateTime

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE coupon SET deleted_at = NOW() WHERE id = ?")
@Table(name = "coupon")
@Entity
class Coupon(
    @Column(name = "name")
    val name: String,
    @Column(name = "discount_rate")
    val discountRate: Int,
    @Column(name = "expired_at")
    val expiredAt: ZonedDateTime,
    @Column(name = "user_id")
    val userId: Long,
    @Column(name = "used_at")
    var usedAt: ZonedDateTime? = null,
    @Column(name = "issued_at")
    val issuedAt: ZonedDateTime,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    fun use() {
        this.usedAt = ZonedDateTime.now()
    }
}