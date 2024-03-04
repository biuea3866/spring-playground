package com.biuea.inflearnbasic

import com.biuea.inflearnbasic.member.Grade
import com.biuea.inflearnbasic.member.Member
import com.biuea.inflearnbasic.member.MemberServiceImpl
import com.biuea.inflearnbasic.order.OrderService
import com.biuea.inflearnbasic.order.OrderServiceImpl
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class OrderApp

fun main(args: Array<String>) {
    val applicationContext = AnnotationConfigApplicationContext(AppConfig::class.java)
    val memberService = applicationContext.getBean("memberService", MemberServiceImpl::class.java)
    val orderService = applicationContext.getBean("orderService", OrderServiceImpl::class.java)

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