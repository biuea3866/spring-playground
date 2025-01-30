package com.biuea.table.infrastructure.redis

import com.biuea.table.domain.user.authentication.UserTokenEntity
import com.biuea.table.domain.user.authentication.UserTokenRepository
import org.springframework.stereotype.Component

@Component
class UserTokenAdapter(private val userTokenRepository: UserTokenRedisRepository): UserTokenRepository {
    override fun saveToken(userToken: UserTokenEntity): UserTokenEntity {
        return userTokenRepository.save(userToken)
    }

    override fun deleteToken(userId: Long) {
        userTokenRepository.deleteByUserId(userId)
    }

    override fun getToken(userId: Long): UserTokenEntity? {
        return userTokenRepository.findByUserId(userId)
    }
}