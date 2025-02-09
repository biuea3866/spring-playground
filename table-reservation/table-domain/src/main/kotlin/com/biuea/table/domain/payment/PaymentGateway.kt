package com.biuea.table.domain.payment

import org.springframework.stereotype.Component

abstract class PaymentGateway {
    abstract val paymentType: PaymentType

    fun pay(
        transactionId: String,
        paymentAmount: Int
    ): PaymentGatewayResponse {
        val connection = this.connectToPay(transactionId, paymentAmount)

        return connection
    }

    fun refund(transactionId: String): PaymentGatewayResponse {
        val connection = this.connectToRefund(transactionId)

        return connection
    }

    fun getPaymentType(): PaymentType {
        return paymentType
    }

    protected abstract fun connectToRefund(transactionId: String): PaymentGatewayResponse

    protected abstract fun connectToPay(
        transactionId: String,
        paymentAmount: Int
    ): PaymentGatewayResponse
}

@Component
class PaymentGatewayRouter(
    private val paymentGateways: List<PaymentGateway>
) {
    fun route(paymentType: PaymentType): PaymentGateway {
        return paymentGateways.firstOrNull { it.paymentType == paymentType }
            ?: throw IllegalArgumentException("Payment gateway not found")
    }
}

data class PaymentGatewayResponse(
    val success: Boolean,
    val transactionId: String,
    val status: PaymentStatus,
    val reason: String? = null
)

enum class PaymentStatus {
    SUCCESS,
    FAILED,
    CANCELLED
}