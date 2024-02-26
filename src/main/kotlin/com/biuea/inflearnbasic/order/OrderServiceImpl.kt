package com.biuea.inflearnbasic.order

import com.biuea.inflearnbasic.discount.DiscountPolicy
import com.biuea.inflearnbasic.discount.FixDiscountPolicy
import com.biuea.inflearnbasic.member.MemberRepository
import com.biuea.inflearnbasic.member.MemoryMemberRepository

class OrderServiceImpl(
    // 생성자 주입
    // 인터페이스에만 의존하고 있으므로 DIP를 지킴.
    // 다만, 인터페이스에 대한 어떤 구현체가 들어올지는 모름
    private val memberRepository: MemberRepository,
    private val discountPolicy: DiscountPolicy
): OrderService {
    // private val memberRepository = MemoryMemberRepository()
    // 할인 정책 변경을 위해 FixDiscountPolicy를 RateDiscountPolicy로 변경
    // DIP는 잘 지켜졌는가? 구현체에 의존을 하고 있으므로, DIP 위반
    // OCP를 잘 지켜졌는가? 의존 구현체를 변경했으므로 클라이언트(service) 코드에 영향을 준다 ~/image/order_discount_diagram4.png
    /* 문제점
    * 클라이언트 코드인 OrderServiceImpl은 DiscountPolicy에 의존하면서, FixDiscountPolicy에도 의존하고 있다.
    * 즉, 구체 클레스가 아닌 인터페이스에 의존하도록 변경해야한다.
    * 해결 방법
    * 누군가가 클라이언트인 OrderServiceImpl에 DiscountPolicy의 구현 객체를 대신 생성하고 주입해주어야 한다.
    */
    // private val discountPolicy: DiscountPolicy = FixDiscountPolicy()
    // private lateinit var discountPolicy: DiscountPolicy // <- DIP는 지켜졌으나 NPE가 발생

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