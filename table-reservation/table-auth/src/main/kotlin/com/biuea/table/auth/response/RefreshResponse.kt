package com.biuea.table.auth.response

data class RefreshResponse(
    val userId: Long,
    val accessToken: String
) {
}