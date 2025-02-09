package com.biuea.table.infrastructure.mysql.payment

import com.biuea.table.domain.payment.Payment
import com.biuea.table.domain.payment.PaymentGateway
import com.biuea.table.domain.payment.PaymentGatewayResponse
import com.biuea.table.domain.payment.PaymentHistory
import com.biuea.table.domain.payment.PaymentRepository
import com.biuea.table.domain.payment.PaymentStatus
import com.biuea.table.infrastructure.mysql.payment.PaymentTransactionJpaRepository
import com.biuea.table.domain.payment.PaymentType
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class PaymentAdapter(
    private val paymentJpaRepository: PaymentJpaRepository,
    private val paymentTransactionJpaRepository: PaymentTransactionJpaRepository
): PaymentRepository, PaymentGateway() {
    override val paymentType: PaymentType = PaymentType.TABLE_PAY

    override fun connectToPay(
        transactionId: String,
        paymentAmount: Int
    ): PaymentGatewayResponse {
        runCatching {
            val paymentTransaction = paymentTransactionJpaRepository.findByIdOrNull(transactionId.toLong())
                ?: throw IllegalArgumentException("Payment transaction not found")
            val payment = paymentTransaction.retrievePayment()
            paymentTransaction.success(paymentAmount)
            payment.pay(paymentTransaction)

            return PaymentGatewayResponse(
                success = true,
                transactionId = transactionId,
                status = PaymentStatus.SUCCESS
            )
        }.getOrElse {
            return PaymentGatewayResponse(
                success = false,
                transactionId = transactionId,
                status = PaymentStatus.FAILED
            )
        }
    }

    override fun connectToRefund(transactionId: String): PaymentGatewayResponse {
        TODO("Not yet implemented")
    }

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
        paymentType: PaymentType
    ): Boolean {
        val paymentHistory = PaymentHistory.create(
            reason = reason,
            amount = amount,
            type = type,
            paymentType = paymentType
        )
        paymentHistory.mapBy(payment)
        payment.addPaymentHistory(paymentHistory)

        return true
    }
}