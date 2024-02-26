package com.biuea.inflearnbasic.member

interface MemberRepository {
    fun save(member: Member)
    fun findById(id: Long): Member
}