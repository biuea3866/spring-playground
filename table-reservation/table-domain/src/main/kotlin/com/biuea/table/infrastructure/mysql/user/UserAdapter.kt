package com.biuea.table.infrastructure.mysql.user

import com.biuea.table.domain.user.UserEntity
import com.biuea.table.domain.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserAdapter(
    private val userJpaRepository: UserJpaRepository
): UserRepository {
    override fun save(user: UserEntity): UserEntity {
        return userJpaRepository.save(user)
    }

    override fun findByEmail(email: String): UserEntity? {
        return userJpaRepository.findByEmail(email)
    }

    override fun findBy(userId: Long): UserEntity? {
        return userJpaRepository.findByIdOrNull(userId)
    }
}