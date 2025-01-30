package com.biuea.table.application.authentication

import com.biuea.table.common.HttpException
import com.biuea.table.domain.user.UserRepository
import com.biuea.table.domain.user.authentication.AuthenticationService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class LogoutApplication(
    private val userRepository: UserRepository,
    private val authenticationService: AuthenticationService
) {
    @Transactional
    fun logout(userId: Long) {
        val user = userRepository.findBy(userId)
            ?: throw HttpException.UnauthorizedException("User not found")
        authenticationService.deleteToken(user.id)
    }
}