package com.biuea.table.infrastructure.mysql.payment

import com.biuea.table.domain.payment.Payment
import com.biuea.table.domain.payment.PaymentHistory
import com.biuea.table.domain.payment.PaymentRepository
import org.springframework.stereotype.Component

@Component
class PaymentAdapter(
    private val paymentJpaRepository: PaymentJpaRepository
): PaymentRepository {
    override fun findBy(id: Long): Payment {
        return paymentJpaRepository.findById(id).get()
    }

    override fun save(payment: Payment): Payment {
        return paymentJpaRepository.save(payment)
    }

    override fun update(
        payment: Payment,
        reason: String,
        amount: Int,
        type: PaymentHistory.Type,
    ): Boolean {
        val paymentHistory = PaymentHistory.create(
            reason = reason,
            amount = amount,
            type = type,
            paymentType = PaymentHistory.PaymentType.CARD
        )
        paymentHistory.mapBy(payment)
        payment.addPaymentHistory(paymentHistory)

        return true
    }
}