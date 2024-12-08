package com.biuea.tablereservingapplication.utils

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class AuthenticationUtils(
    @Value("\${jwt.secret}")
    private val secretKey: String,
    @Value("\${jwt.expiration-time}")
    private val expirationTime: Long
) {
    private val key: Key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    fun generateToken(
        email: String,
        userId: Long
    ): String {
        return Jwts.builder()
            .setSubject(email) // 토큰 주체
            .setClaims(mapOf( // 토큰 내에 저장할 데이터
                "userId" to userId,
                "email" to email
            ))
            .setIssuedAt(Date()) // 토큰 발급 시간
            .setExpiration(Date(System.currentTimeMillis() + expirationTime)) // 토큰 만료 시간
            .signWith(key, SignatureAlgorithm.ES256) // 토큰 서명
            .compact()
    }

    fun extractValue(
        token: String,
        variableKey: String
    ): String {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
            .get(variableKey, String::class.java)
    }

    fun extractClaims(token: String): Map<String, *> {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun isTokenValid(token: String): Boolean {
        return runCatching {
            this.extractClaims(token)
            true
        }.getOrElse { false }
    }
}