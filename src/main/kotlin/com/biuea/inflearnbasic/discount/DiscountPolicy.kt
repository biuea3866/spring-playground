package com.biuea.inflearnbasic.discount

import com.biuea.inflearnbasic.member.Member

interface DiscountPolicy {
    /**
     * @return 할인 대상 금액
     */
    fun discount(member: Member, price: Int): Int
}