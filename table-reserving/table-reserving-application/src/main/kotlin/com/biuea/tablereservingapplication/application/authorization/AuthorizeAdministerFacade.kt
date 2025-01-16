package com.biuea.tablereservingapplication.application.authorization

import com.biuea.tablereservingapplication.domain.administer.AdministerRepository
import com.biuea.tablereservingapplication.domain.user.aggregate.UserGrade
import com.biuea.tablereservingapplication.domain.user.repository.UserRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class AuthorizeAdministerFacade(
    private val administerRepository: AdministerRepository
): AuthorizationPattern() {
    @Transactional(readOnly = true)
    override fun<T> execute(argument: T) {
        require(argument is AuthorizeAdministerInput) { throw IllegalArgumentException("Not supported") }

        val administer = administerRepository.findById(argument.administerId)
            ?: throw IllegalArgumentException("User not found")
        administer.checkGranted()
    }
}

data class AuthorizeAdministerInput(
    val administerId: Long
)