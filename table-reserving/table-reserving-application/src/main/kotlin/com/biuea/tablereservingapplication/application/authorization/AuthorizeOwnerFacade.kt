package com.biuea.tablereservingapplication.application.authorization

import com.biuea.tablereservingapplication.core.convertLongToId
import com.biuea.tablereservingapplication.domain.owner.repository.OwnerRepository
import com.biuea.tablereservingapplication.domain.user.aggregate.UserGrade
import com.biuea.tablereservingapplication.domain.user.repository.UserRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class AuthorizeOwnerFacade(
    private val ownerRepository: OwnerRepository
): AuthorizationPattern() {
    @Transactional(readOnly = true)
    override fun<T> execute(argument: T) {
        require(argument is AuthorizeOwnerInput) { throw IllegalArgumentException("Not supported") }
        ownerRepository.findById(argument.ownerId.convertLongToId())
            ?: throw IllegalArgumentException("User not found")
    }
}

data class AuthorizeOwnerInput(
    val ownerId: Long
)