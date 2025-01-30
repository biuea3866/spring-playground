package com.biuea.table.domain.user

interface UserRepository {
    fun save(user: UserEntity): UserEntity
    fun findByEmail(email: String): UserEntity?
    fun findBy(userId: Long): UserEntity?
}