package com.biuea.inflearnbasic.member

import org.springframework.stereotype.Service

class MemberServiceImpl: MemberService {
    // DIP 없이 구현체 직접 생성
    private val memberRepository: MemberRepository = MemoryMemberRepository()

    override fun findMember(memberId: Long): Member? = memberRepository.findById(memberId)

    override fun join(member: Member) = memberRepository.save(member)
}