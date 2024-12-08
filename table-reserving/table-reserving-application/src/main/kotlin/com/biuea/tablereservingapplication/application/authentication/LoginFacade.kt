package com.biuea.tablereservingapplication.application.authentication

import com.biuea.tablereservingapplication.core.HttpException
import com.biuea.tablereservingapplication.domain.user.repository.UserRepository
import com.biuea.tablereservingapplication.utils.AuthenticationUtils
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class LoginFacade(
    private val authenticationUtils: AuthenticationUtils,
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val tokenManagementService: TokenManagementService
) {
    @Transactional(readOnly = true)
    fun login(
        email: String,
        password: String
    ): Pair<String, String> {
        val user = userRepository.findByEmail(email)
            ?: throw HttpException.UnauthorizedException("User not found")
        user.validatePassword { bCryptPasswordEncoder.matches(password, it) }
        val accessToken = authenticationUtils.generateAccessToken(email, user.id.id)
        val refreshToken = authenticationUtils.generateRefreshToken(email, user.id.id)
        this.tokenManagementService.saveToken(refreshToken, email)
        return Pair(accessToken, refreshToken)
    }

    @Transactional(readOnly = true)
    fun refresh(refreshToken: String): Pair<String, String> {
        val email = authenticationUtils.extractValue(refreshToken, "email")
        val user = userRepository.findByEmail(email)
            ?: throw HttpException.UnauthorizedException("User not found")
        val accessToken = authenticationUtils.generateAccessToken(email, user.id.id)

        return Pair(accessToken, refreshToken)
    }
}