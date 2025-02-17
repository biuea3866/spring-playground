package com.biuea.table.auth

import com.biuea.table.application.authentication.LoginApplication
import com.biuea.table.application.authentication.RefreshApplication
import com.biuea.table.application.authentication.SignUpApplication
import com.biuea.table.application.authentication.SignUpCommand
import com.biuea.table.application.authentication.TokenApplication
import com.biuea.table.auth.request.LoginRequest
import com.biuea.table.auth.request.SignUpRequest
import com.biuea.table.auth.response.LoginResponse
import com.biuea.table.auth.response.RefreshResponse
import com.biuea.table.common.ApiResponse
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController("/authentication")
class AuthenticationApiController(
    private val loginApplication: LoginApplication,
    private val signUpApplication: SignUpApplication,
    private val refreshApplication: RefreshApplication,
    private val tokenApplication: TokenApplication
) {
    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest
    ): ApiResponse<LoginResponse> {
        val loginResponse = loginApplication.login(request.email, request.password)
        return ApiResponse.success(LoginResponse.of(loginResponse.userId, loginResponse.accessToken))
    }

    @DeleteMapping("/logout")
    fun logout() {}

    @PatchMapping("/refresh")
    fun refresh(
        @RequestHeader("X-User-Id") userId: Long
    ): ApiResponse<RefreshResponse> {
        val refreshToken = refreshApplication.refresh(userId)
        return ApiResponse.success(RefreshResponse(refreshToken.userId, refreshToken.accessToken))
    }

    @PostMapping("/signup")
    fun signup(
        @RequestBody request: SignUpRequest
    ): ApiResponse<Unit> {
        signUpApplication.signUp(
            SignUpCommand(
                email = request.email,
                password = request.password,
                phoneNumber = request.phoneNumber,
                nickname = request.nickname,
                s3Key = request.s3Key,
                s3Bucket = request.s3Bucket,
                filename = request.filename
            )
        )

        return ApiResponse.success(Unit)
    }

    @PostMapping("/token/validate")
    fun validateToken(
        @RequestHeader("Authorization") token: String
    ): ApiResponse<String> {
        return ApiResponse.success(tokenApplication.validate(token))
    }
}