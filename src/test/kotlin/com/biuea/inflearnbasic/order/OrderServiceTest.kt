package com.biuea.inflearnbasic.order

import com.biuea.inflearnbasic.AppConfig
import com.biuea.inflearnbasic.member.Grade
import com.biuea.inflearnbasic.member.Member
import com.biuea.inflearnbasic.member.MemberService
import com.biuea.inflearnbasic.member.MemberServiceImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OrderServiceTest {
    lateinit var memberService: MemberService
    lateinit var orderService: OrderService

    @BeforeEach
    fun setUp() {
        val appConfig = AppConfig()
        this.memberService = appConfig.memberService()
        this.orderService = appConfig.orderService()
    }

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

        // when
        val order = orderService.createOrder(
            1L,
            "itemA",
            10000
        )

        // then
        Assertions.assertEquals(order.discountPrice, 1000)
    }
}