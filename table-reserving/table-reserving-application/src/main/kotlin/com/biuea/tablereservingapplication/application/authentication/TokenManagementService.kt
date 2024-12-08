package com.biuea.tablereservingapplication.application.authentication

class TokenManagementService(
    private val redisTemplate: StringRedisTemplate
) {
    fun saveToken(token: String, email: String) {
        redisTemplate.opsForValue().set(email, token)
    }

    fun getToken(email: String): String {
        return redisTemplate.opsForValue().get(email)
    }

    fun deleteToken(email: String) {
        redisTemplate.delete(email)
    }
}