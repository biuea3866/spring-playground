package com.biuea.inflearnbasic

import com.biuea.inflearnbasic.discount.FixDiscountPolicy
import com.biuea.inflearnbasic.member.MemberService
import com.biuea.inflearnbasic.member.MemberServiceImpl
import com.biuea.inflearnbasic.member.MemoryMemberRepository
import com.biuea.inflearnbasic.order.OrderService
import com.biuea.inflearnbasic.order.OrderServiceImpl

/**
 *  구성과 역할을 분리하기 위한 클래스
 *  client(service)에 대해 어떤 구현 객체가 주입될지를 결정하는 클래스
 *  이로 인해 클라이언트 코드는 구현 객체를 몰라도 되며, 클라이언트의 코드를 변경하지 않고도 구현 객체를 변경할 수 있다. 인터페이스를 의존하기 때문
 */
class AppConfig {
    fun memberService(): MemberService {
        return MemberServiceImpl(MemoryMemberRepository())
    }

    fun orderService(): OrderService {
        return OrderServiceImpl(MemoryMemberRepository(), FixDiscountPolicy())
    }
}