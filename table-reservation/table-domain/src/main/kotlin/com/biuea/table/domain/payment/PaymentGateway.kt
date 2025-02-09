package com.biuea.table.domain.payment

abstract class PaymentGateway {
    abstract val paymentType: PaymentType

    fun pay(paymentAmount: Int): Boolean {
        val connection = this.connect(paymentAmount)

        return true
    }

    protected abstract fun connect(paymentAmount: Int): Boolean
}