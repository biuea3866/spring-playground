package com.biuea.table.domain.payment

import jakarta.persistence.Column
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.ZonedDateTime

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE payment_history SET deleted_at = NOW() WHERE id = ?")
@Table(name = "payment_history")
@Entity
class PaymentHistory(
    @Column(name = "reason")
    val reason: String,

    @Column(name = "amount")
    val amount: Int,

    @Column(name = "created_at")
    val createdAt: ZonedDateTime,

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    val type: Type,

    @Column(name = "payment_type")
    @Enumerated(EnumType.STRING)
    val paymentType: PaymentType,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    fun mapBy(payment: Payment) {
        this.payment = payment
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "payment_id",
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    var payment: Payment? = null

    enum class Type {
        PAYMENT,
        REFUND
    }

    companion object {
        fun create(
            reason: String,
            amount: Int,
            type: Type,
            paymentType: PaymentType
        ): PaymentHistory {
            return PaymentHistory(
                reason = reason,
                amount = amount,
                createdAt = ZonedDateTime.now(),
                type = type,
                paymentType = paymentType
            )
        }
    }
}