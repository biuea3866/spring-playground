package com.biuea.inflearnbasic.member

import org.springframework.stereotype.Service

class MemberServiceImpl(
    // 생성자 주입
    private var memberRepository: MemberRepository
) : MemberService {

    override fun findMember(memberId: Long): Member = memberRepository.findById(memberId)

    override fun join(member: Member) = memberRepository.save(member)
}