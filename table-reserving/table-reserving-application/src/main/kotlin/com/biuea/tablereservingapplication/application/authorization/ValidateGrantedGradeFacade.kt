package com.biuea.tablereservingapplication.application.authorization

import com.biuea.tablereservingapplication.core.Id
import com.biuea.tablereservingapplication.domain.user.aggregate.UserGrade
import com.biuea.tablereservingapplication.domain.user.repository.UserRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ValidateGrantedGradeFacade(
    private val userRepository: UserRepository
) {
    @Transactional(readOnly = true)
    fun execute(
        userId: Long,
        grade: UserGrade
    ) {
        val user = userRepository.findById(userId)
            ?: throw IllegalArgumentException("User not found")
        user.checkGranted(grade)
    }
}