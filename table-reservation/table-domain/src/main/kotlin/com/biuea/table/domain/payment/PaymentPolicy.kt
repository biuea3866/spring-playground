package com.biuea.table.domain.payment

interface PaymentPolicy {
    fun calculate(paymentAmount: Int): Int
}

class PointPaymentPolicy(
    private val point: Point,
) : PaymentPolicy {
    override fun calculate(paymentAmount: Int): Int {
        return point.amount - paymentAmount
    }
}

class CouponPaymentPolicy(
    private val coupon: Coupon
) : PaymentPolicy {
    override fun calculate(paymentAmount: Int): Int {
        coupon.use()
        return paymentAmount - (paymentAmount * coupon.discountRate).div(100)
    }
}

class SelfDiscountPolicy(
    private val discountPoint: Int
) : PaymentPolicy {
    override fun calculate(paymentAmount: Int): Int {
        return paymentAmount - discountPoint
    }
}