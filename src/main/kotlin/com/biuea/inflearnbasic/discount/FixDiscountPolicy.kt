package com.biuea.inflearnbasic.discount

import com.biuea.inflearnbasic.member.Grade
import com.biuea.inflearnbasic.member.Member

class FixDiscountPolicy: DiscountPolicy {
    private val discountFixAmount = 1000

    override fun discount(member: Member, price: Int): Int {
        return when (member.grade == Grade.VIP) {
            true -> discountFixAmount
            false -> 0
        }
    }
}