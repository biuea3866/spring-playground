package com.biuea.inflearnbasic.order

import com.biuea.inflearnbasic.discount.FixDiscountPolicy
import com.biuea.inflearnbasic.member.MemoryMemberRepository

class OrderServiceImpl: OrderService {
    private val memberRepository = MemoryMemberRepository()
    private val discountPolicy = FixDiscountPolicy()

    override fun createOrder(
        memberId: Long,
        itemName: String,
        itemPrice: Int
    ): Order {
        val member = memberRepository.findById(memberId)
        val discountPrice = discountPolicy.discount(
            member = member,
            price = itemPrice
        )

        return Order(
            memberId = memberId,
            itemName = itemName,
            itemPrice = itemPrice,
            discountPrice = discountPrice
        )
    }
}