package com.biuea.table.application.authentication

import com.biuea.table.common.HttpException
import com.biuea.table.domain.user.UserRepository
import com.biuea.table.domain.user.authentication.AuthenticationService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class RefreshApplication(
    private val userRepository: UserRepository,
    private val authenticationService: AuthenticationService
) {
    @Transactional
    fun refresh(userId: Long): RefreshToken {
        val user = userRepository.findBy(userId)
            ?: throw HttpException.UnauthorizedException("User not found")
        val userToken = authenticationService.refreshToken(user.email, user.id)

        return RefreshToken(
            userId = user.id,
            accessToken = userToken.accessToken
        )
    }
}

data class RefreshToken(
    val userId: Long,
    val accessToken: String
)