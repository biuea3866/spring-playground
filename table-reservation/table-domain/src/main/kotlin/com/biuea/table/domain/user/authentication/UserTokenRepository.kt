package com.biuea.table.domain.user.authentication

interface UserTokenRepository {
    fun saveToken(userToken: UserTokenEntity): UserTokenEntity

    fun getToken(userId: Long): UserTokenEntity?

    fun deleteToken(userId: Long)
}