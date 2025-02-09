package com.biuea.table.domain.payment

class PaymentDiscount(
    val paymentAmount: Int,
) {
    val discountPolicy: MutableSet<PaymentPolicy> = mutableSetOf()

    fun addDiscountPolicy(paymentPolicy: PaymentPolicy) {
        discountPolicy.add(paymentPolicy)
    }

    fun calculate(): Int {
        val result = discountPolicy.fold(paymentAmount) { result, it ->
            result + it.calculate(result)
        }

        require(result >= 0) { "Discounted amount must be greater than or equal to 0" }

        return result
    }
}