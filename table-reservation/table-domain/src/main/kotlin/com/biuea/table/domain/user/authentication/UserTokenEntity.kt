package com.biuea.table.domain.user.authentication

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "user_token", timeToLive = 60 * 60 * 24 * 7)
class UserTokenEntity(
    @Id
    val userId: Long = 0L,
    @Indexed
    var accessToken: String,
    @Indexed
    var refreshToken: String,
    @TimeToLive
    val timeToLive: Long
) {

    fun refresh(accessToken: String, refreshToken: String) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
    }
}