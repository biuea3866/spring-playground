package com.biuea.tablereservingapplication.application.authorization

import com.biuea.tablereservingapplication.domain.user.aggregate.UserGrade
import com.biuea.tablereservingapplication.domain.user.repository.UserRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class AuthorizePlatformUserFacade(
    private val userRepository: UserRepository
): AuthorizationPattern() {
    @Transactional(readOnly = true)
    override fun<T> execute(argument: T) {
        require(argument is AuthorizePlatformUserInput) { throw IllegalArgumentException("Not supported") }
        val user = userRepository.findById(argument.userId)
            ?: throw IllegalArgumentException("User not found")
        user.checkGranted(argument.grade)
    }
}

data class AuthorizePlatformUserInput(
    val userId: Long,
    val grade: UserGrade
)