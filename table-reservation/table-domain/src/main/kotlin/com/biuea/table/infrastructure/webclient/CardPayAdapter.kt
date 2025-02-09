package com.biuea.table.infrastructure.webclient

import com.biuea.table.domain.payment.PaymentGateway
import com.biuea.table.domain.payment.PaymentType
import org.springframework.stereotype.Component

@Component
class CardPayAdapter() : PaymentGateway() {
    override val paymentType: PaymentType = PaymentType.CARD

    override fun connect(paymentAmount: Int): Boolean {
        return true
    }
}