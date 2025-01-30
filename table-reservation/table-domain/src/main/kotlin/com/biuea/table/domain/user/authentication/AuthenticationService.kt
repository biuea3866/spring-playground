package com.biuea.table.domain.user.authentication

import com.biuea.table.domain.user.UserEntity
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class AuthenticationService(
    @Value("\${jwt.secret}")
    private val secretKey: String,
    @Value("\${jwt.access.token.expire-time}")
    private val accessExpirationTime: Long,
    @Value("\${jwt.refresh.token.expire-time}")
    private val refreshExpirationTime: Long,
    private val userTokenRepository: UserTokenRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {
    private val key: Key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    fun generateAccessToken(
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
            .setExpiration(Date(System.currentTimeMillis() + accessExpirationTime)) // 토큰 만료 시간
            .signWith(key, SignatureAlgorithm.ES256) // 토큰 서명
            .compact()
    }

    fun generateRefreshToken(
        email: String,
        userId: Long
    ): String {
        return Jwts.builder()
            .setSubject(email)
            .setClaims(mapOf(
                "userId" to userId,
                "email" to email
            ))
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + refreshExpirationTime))
            .signWith(key, SignatureAlgorithm.ES256)
            .compact()
    }

    fun isTokenExpired(token: String): Boolean {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
            .expiration
            .before(Date())
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

    fun saveToken(
        email: String,
        userId: Long
    ): UserTokenEntity {
        val accessToken = this.generateAccessToken(email, userId)
        val refreshToken = this.generateRefreshToken(email, userId)
        val userToken = UserTokenEntity(
            userId = userId,
            accessToken = accessToken,
            refreshToken = refreshToken,
            timeToLive = 60 * 60 * 24 * 7
        )
        return this.userTokenRepository.saveToken(userToken)
    }

    fun refreshToken(
        email: String,
        userId: Long
    ): UserTokenEntity {
        val userToken = this.userTokenRepository.getToken(userId)
            ?: throw IllegalArgumentException("User token not found")
        val accessToken = this.generateAccessToken(email, userId)
        userToken.refresh(accessToken, userToken.refreshToken)
        return userToken
    }

    fun deleteToken(userId: Long) {
        this.userTokenRepository.deleteToken(userId)
    }

    fun encodePassword(user: UserEntity): String {
        return this.bCryptPasswordEncoder.encode(user.password)
    }

    fun matchPassword(user: UserEntity, rawPassword: String) {
        if (!this.bCryptPasswordEncoder.matches(rawPassword, user.password)) {
            throw IllegalArgumentException("Password does not match")
        }
    }
}