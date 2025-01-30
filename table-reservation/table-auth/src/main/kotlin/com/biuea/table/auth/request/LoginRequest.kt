package com.biuea.table.auth.request

data class LoginRequest(
    val email: String,
    val password: String
) {
}