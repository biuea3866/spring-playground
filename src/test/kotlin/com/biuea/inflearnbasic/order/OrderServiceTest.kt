package com.biuea.inflearnbasic.order

import com.biuea.inflearnbasic.member.Grade
import com.biuea.inflearnbasic.member.Member
import com.biuea.inflearnbasic.member.MemberServiceImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class OrderServiceTest {
    val memberService = MemberServiceImpl()
    val orderService = OrderServiceImpl()

    @Test
    fun `주문 생성`() {
        // given
        val memberId = 1L
        val member = Member(
            id = memberId,
            name = "memberA",
            grade = Grade.VIP
        )
        memberService.join(member)
        val findMember = memberService.findMember(memberId)
        // when
        val order = orderService.createOrder(
            findMember.id,
            "itemA",
            10000
        )

        // then
        Assertions.assertEquals(order.discountPrice, 1000)
    }
}