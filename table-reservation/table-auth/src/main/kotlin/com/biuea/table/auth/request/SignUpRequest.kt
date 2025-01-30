package com.biuea.table.auth.request

data class SignUpRequest(
    val email: String,
    val password: String,
    val nickname: String,
    val phoneNumber: String,
    val s3Key: String?,
    val s3Bucket: String?,
    val filename: String?
) {
}