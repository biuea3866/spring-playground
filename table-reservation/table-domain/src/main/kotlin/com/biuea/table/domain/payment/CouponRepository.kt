package com.biuea.table.domain.payment

interface CouponRepository {
    fun findBy(id: Long): Coupon?
}