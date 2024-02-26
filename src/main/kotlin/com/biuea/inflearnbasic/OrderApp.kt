package com.biuea.inflearnbasic

import com.biuea.inflearnbasic.member.Grade
import com.biuea.inflearnbasic.member.Member
import com.biuea.inflearnbasic.member.MemberServiceImpl
import com.biuea.inflearnbasic.order.OrderService
import com.biuea.inflearnbasic.order.OrderServiceImpl

class OrderApp {
    fun main(args: Array<String>) {
        val memberService = MemberServiceImpl()
        val orderService = OrderServiceImpl()

        val memberId = 1L
        val member = Member(
            id = memberId,
            name = "memberA",
            grade = Grade.VIP
        )
        memberService.join(member)
        val order = orderService.createOrder(memberId, "itemA", 10000)

        println("order = $order")
        println("order.calculatePrice = ${order.calculatePrice()}")
    }
}