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
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {
    @Transactional(readOnly = true)
    fun login(
        email: String,
        password: String
    ): String {
        val user = userRepository.findByEmail(email)
            ?: throw HttpException.UnauthorizedException("User not found")
        user.validatePassword { bCryptPasswordEncoder.matches(password, it) }
        return authenticationUtils.generateToken(email, user.id.id)
    }
}