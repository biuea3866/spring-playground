package com.biuea.table.infrastructure.mysql.user

import com.biuea.table.domain.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository: JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
}