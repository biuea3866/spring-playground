package com.biuea.tablereservingapplication.application.authentication

import org.springframework.data.redis.core.StringRedisTemplate

class TokenManagementService(
    private val redisTemplate: StringRedisTemplate
) {
    fun saveToken(token: String, email: String) {
//        redisTemplate.opsForValue()
    }

    fun getToken(email: String): String {
//        return redisTemplate.opsForValue().get(email)
        return ""
    }

    fun deleteToken(email: String) {
//        redisTemplate.delete(email)
    }
}