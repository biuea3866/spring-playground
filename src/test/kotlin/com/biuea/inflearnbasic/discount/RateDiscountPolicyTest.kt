package com.biuea.inflearnbasic.discount

import com.biuea.inflearnbasic.member.Grade
import com.biuea.inflearnbasic.member.Member
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class RateDiscountPolicyTest {
    private val discountPolicy = RateDiscountPolicy()

    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다")
    fun vip_o() {
        // given
        val member = Member(1L, "memberVIP", Grade.VIP)

        // when
        val discount = discountPolicy.discount(member, 10000)

        // then
        Assertions.assertEquals(discount, 1000)
    }

    @Test
    @DisplayName("VIP가 아니면 할인이 적용되지 않아야 한다")
    fun vip_x() {
        // given
        val member = Member(2L, "memberBasic", Grade.BASIC)

        // when
        val discount = discountPolicy.discount(member, 10000)

        // then
        Assertions.assertEquals(discount, 0)
    }
}