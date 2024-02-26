package com.biuea.inflearnbasic.member

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MemberServiceText {
    private val memberService = MemberServiceImpl()

    @Test
    fun join() {
        val member = Member(1L, "memberA", Grade.VIP)

        memberService.join(member)
        val findMember = memberService.findMember(1L)

        Assertions.assertEquals(member, findMember)
    }
}