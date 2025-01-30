package com.biuea.table.authentication

import com.biuea.table.application.authentication.LoginApplication
import com.biuea.table.authentication.request.LoginRequest
import com.biuea.table.authentication.response.LoginResponse
import com.biuea.table.common.ApiResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/authentication")
class AuthenticationApiController(
    private val loginApplication: LoginApplication
) {
    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest
    ): ApiResponse<LoginResponse> {
        val loginResponse = loginApplication.login(request.email, request.password)
        return ApiResponse.success(loginResponse)
    }

    @DeleteMapping("/authentication/logout")
    fun logout() {}

    @PatchMapping("/authentication/refresh")
    fun refresh() {}

    @PostMapping("/authentication/signup")
    fun signup() {}
}