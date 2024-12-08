package com.biuea.tablereservingapplication.domain.user.vo

data class AuthenticationInfo(
    val email: String,
    val password: String
) {
    fun validatePassword(matchPassword: (password: String) -> Unit) {
        matchPassword(this.password)
    }
}