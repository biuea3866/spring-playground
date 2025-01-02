package com.biuea.tablereservingapplication.core

import org.springframework.http.HttpStatus

data class ApiResponse<T>(
    val success: Boolean,
    val status: HttpStatus,
    val message: String?,
    val data: T
) {
    companion object {
        fun success(httpStatus: HttpStatus): ApiResponse<Boolean> {
            return ApiResponse(
                true,
                httpStatus,
                null,
                true
            )
        }

        fun <T> success(
            httpStatus: HttpStatus,
            data: T
        ): ApiResponse<T> {
            return ApiResponse(
                true,
                httpStatus,
                null,
                data
            )
        }
    }
}