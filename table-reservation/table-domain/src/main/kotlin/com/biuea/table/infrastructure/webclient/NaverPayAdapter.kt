package com.biuea.table.infrastructure.webclient

import com.biuea.table.domain.payment.PaymentGateway
import com.biuea.table.domain.payment.PaymentGatewayResponse
import com.biuea.table.domain.payment.PaymentType
import org.springframework.stereotype.Component

@Component
class NaverPayAdapter: PaymentGateway() {
    override val paymentType = PaymentType.NAVER_PAY

    override fun buildPay(transactionId: String, paymentAmount: Int): PaymentGatewayResponse {
        TODO("Not yet implemented")
    }

    override fun buildRefund(transactionId: String): PaymentGatewayResponse {
        TODO("Not yet implemented")
    }
}