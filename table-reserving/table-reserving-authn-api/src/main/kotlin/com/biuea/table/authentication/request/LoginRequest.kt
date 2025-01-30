package com.biuea.table.authentication.request

data class LoginRequest(
    val email: String,
    val password: String
) {
}