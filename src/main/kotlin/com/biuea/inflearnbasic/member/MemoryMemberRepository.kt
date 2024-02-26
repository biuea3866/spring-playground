package com.biuea.inflearnbasic.member

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

class MemoryMemberRepository: MemberRepository {
    // 로컬 캐시(임시)
    // 동시성 이슈로 인해 HashMap 대신 ConcurrentHashMap 사용
    private val localCache: ConcurrentHashMap<Long, Member> = ConcurrentHashMap()

    override fun save(member: Member) {
        localCache[member.id] = member
    }

    override fun findById(id: Long): Member {
        println("cache: ${localCache[id]}")
        return localCache[id] ?: throw IllegalArgumentException("해당 ID의 회원이 존재하지 않습니다.")
    }
}