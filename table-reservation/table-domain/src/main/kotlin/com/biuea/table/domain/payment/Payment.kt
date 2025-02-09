package com.biuea.table.domain.payment

import jakarta.persistence.CascadeType
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
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.ZonedDateTime

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE payment SET deleted_at = NOW() WHERE id = ?")
@Table(name = "payment")
@Entity
class Payment(
    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "amount")
    val amount: Int,

    @OneToMany(
        mappedBy = "payment",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
    )
    val paymentHistories: MutableSet<PaymentHistory>,

    @OneToMany(
        mappedBy = "payment",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
    )
    val paymentTransactions: MutableSet<PaymentTransaction>
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    fun addPaymentHistory(paymentHistory: PaymentHistory) {
        paymentHistories.add(paymentHistory)
    }

    fun pay(paymentTransaction: PaymentTransaction) {
        paymentTransactions.add(paymentTransaction)
    }
}