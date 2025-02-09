package com.biuea.table.application.authentication

import com.biuea.table.domain.user.authentication.AuthenticationService
import org.springframework.stereotype.Component

@Component
class TokenApplication(
    private val authenticationService: AuthenticationService
) {
    fun validate(token: String): String {
        authenticationService.checkToken(token)
        return authenticationService.extractValue("userId", token)
    }
}