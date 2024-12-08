package com.biuea.tablereservingapplication.utils

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtUtil(
    @Value("\${jwt.secret}")
    private val secretKey: String,
    @Value("\${jwt.expiration-time}")
    private val expirationTime: Long
) {
}