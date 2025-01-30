package com.biuea.table.infrastructure.redis

import com.biuea.table.domain.user.authentication.UserTokenEntity
import org.springframework.data.repository.CrudRepository

interface UserTokenRedisRepository: CrudRepository<UserTokenEntity, Long> {
    fun findByUserId(userId: Long): UserTokenEntity?
    fun deleteByUserId(userId: Long)
}