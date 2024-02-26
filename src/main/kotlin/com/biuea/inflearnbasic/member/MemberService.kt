package com.biuea.inflearnbasic.member

interface MemberService {
    fun join(member: Member)

    fun findMember(memberId: Long): Member?
}