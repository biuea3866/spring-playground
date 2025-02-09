package com.biuea.table.domain.payment

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
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
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE payment_transcation SET deleted_at = NOW() WHERE id = ?")
@Table(name = "payment_transcation")
@Entity
class PaymentTransaction(
    @Column(name = "amount")
    var amount: Int,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: PaymentTransactionStatus,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "payment_id",
        foreignKey = ForeignKey(name = "fk_payment_transaction_payment_id")
    )
    val payment: Payment? = null

    fun success(paymentAmount: Int) {
        status = PaymentTransactionStatus.SUCCESS
        amount = paymentAmount
    }

    fun retrievePayment(): Payment {
        return payment?: throw IllegalArgumentException("Payment not found")
    }
}

enum class PaymentTransactionStatus {
    READY,
    SUCCESS,
    FAILED,
    CANCELLED
}