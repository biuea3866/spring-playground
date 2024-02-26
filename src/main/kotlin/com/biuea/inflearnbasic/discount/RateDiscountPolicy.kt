package com.biuea.inflearnbasic.discount

import com.biuea.inflearnbasic.member.Grade
import com.biuea.inflearnbasic.member.Member

class RateDiscountPolicy: DiscountPolicy {
    private val discountPercent = 10

    override fun discount(member: Member, price: Int): Int {
        return when(member.grade) {
            Grade.VIP -> price * discountPercent / 100
            else -> 0
        }
    }
}