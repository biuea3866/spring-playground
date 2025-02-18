package com.biuea.table.domain.payment

import org.springframework.stereotype.Component

abstract class PaymentGateway {
    protected abstract val paymentType: PaymentType

    fun getPaymentType(): PaymentType {
        return paymentType
    }
}

class PayProcessor(
    private val paymentGatewayRouter: PaymentGatewayRouter
) {
    fun pay(transactionId: String, paymentAmount: Int): PaymentGatewayResponse {

    }
}

class PayApplication {
    fun pay(paymentType: PaymentType, transactionId: String, paymentAmount: Int): PaymentGatewayResponse {

    }
}

class KakaoPay: PaymentGateway() {
    override val paymentType = PaymentType.KAKAO_PAY
    lateinit var payStrategy: PayStrategy

    fun setPayStrategy(payStrategy: PayStrategy) {
        this.payStrategy = payStrategy
    }

    fun pay(transactionId: String, paymentAmount: Int): PaymentGatewayResponse {
        return PaymentGatewayResponse(
            success = true,
            transactionId = transactionId,
            status = PaymentStatus.SUCCESS
        )
    }
}

interface PayStrategy {
    fun pay(transactionId: String, paymentAmount: Int): PaymentGatewayResponse
}

interface RefundStrategy {
    fun refund(transactionId: String): PaymentGatewayResponse
}

enum class PaymentType {
    KAKAO_PAY,
    NAVER_PAY,
    TOSS_PAY,
    CARD_PAY
}

@Component
class PaymentGatewayRouter(
    private val paymentGateways: List<PaymentGateway>
) {
    fun route(paymentType: PaymentType): PaymentGateway {
        return paymentGateways.firstOrNull { it.getPaymentType() == paymentType }
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