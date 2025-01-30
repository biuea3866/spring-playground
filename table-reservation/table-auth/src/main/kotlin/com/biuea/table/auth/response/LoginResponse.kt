package com.biuea.table.auth.response

class LoginResponse(
    val userId: Long,
    val accessToken: String
) {
    companion object {
        fun of(userId: Long, accessToken: String): LoginResponse {
            return LoginResponse(
                userId = userId,
                accessToken = accessToken
            )
        }
    }
}