package com.biuea.table.application.authentication

import com.biuea.table.common.HttpException
import com.biuea.table.domain.user.UserRepository
import com.biuea.table.domain.user.authentication.AuthenticationService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class LoginApplication(
    private val userRepository: UserRepository,
    private val authenticationService: AuthenticationService,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {
    @Transactional(readOnly = true)
    fun login(
        email: String,
        password: String
    ): UserToken {
        val user = userRepository.findByEmail(email)
            ?: throw HttpException.UnauthorizedException("User not found")
        authenticationService.matchPassword(user, password)
        val userToken = this.authenticationService.saveToken(email, user.id)
        return UserToken(
            userId = user.id,
            accessToken = userToken.accessToken,
            refreshToken = userToken.refreshToken
        )
    }
}

data class UserToken(
    val userId: Long,
    val accessToken: String,
    val refreshToken: String
)