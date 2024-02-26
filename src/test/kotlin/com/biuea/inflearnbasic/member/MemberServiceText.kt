package com.biuea.inflearnbasic.member

import com.biuea.inflearnbasic.AppConfig
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MemberServiceText {
    private lateinit var memberService: MemberService

    @BeforeEach
    fun beforeEach() {
        val appConfig = AppConfig()
        this.memberService = appConfig.memberService()
    }

    @Test
    fun join() {
        val member = Member(1L, "memberA", Grade.VIP)

        memberService.join(member)
        val findMember = memberService.findMember(1L)

        Assertions.assertEquals(member, findMember)
    }
}