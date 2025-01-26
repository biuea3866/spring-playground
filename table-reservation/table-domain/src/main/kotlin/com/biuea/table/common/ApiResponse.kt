package com.biuea.table.common

class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T?
) {
    companion object {
        fun <T> success(data: T): ApiResponse<T> {
            return ApiResponse(true, "success", data)
        }

        fun <T> fail(message: String): ApiResponse<T> {
            return ApiResponse(false, message, null)
        }
    }
}