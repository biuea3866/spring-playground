package com.biuea.inflearnbasic

import com.biuea.inflearnbasic.member.Grade
import com.biuea.inflearnbasic.member.Member
import com.biuea.inflearnbasic.member.MemberService
import com.biuea.inflearnbasic.member.MemberServiceImpl

class MemberApp {
    fun main(args: Array<String>) {
        val appConfig = AppConfig()
        val memberService = appConfig.memberService()
        val member = Member(
            id = 1L,
            name = "memberA",
            grade = Grade.VIP
        )
        memberService.join(member)
        val member1 = memberService.findMember(1L)
    }
}