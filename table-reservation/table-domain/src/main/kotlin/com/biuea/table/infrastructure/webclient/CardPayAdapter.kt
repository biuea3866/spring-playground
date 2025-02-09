package com.biuea.table.infrastructure.webclient

import com.biuea.table.domain.payment.PaymentGateway
import org.springframework.stereotype.Component

@Component
class CardPayAdapter: PaymentGateway() {
    override fun connect(paymentAmount: Int): Boolean {
        return true
    }
}