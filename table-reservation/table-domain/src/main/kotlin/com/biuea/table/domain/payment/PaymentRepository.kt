package com.biuea.table.domain.payment

interface PaymentRepository {
    fun findBy(userId: Long): Payment?
    fun save(payment: Payment): Payment
    fun update(
        payment: Payment,
        reason: String,
        amount: Int,
        type: PaymentHistory.Type,
    ): Boolean
}