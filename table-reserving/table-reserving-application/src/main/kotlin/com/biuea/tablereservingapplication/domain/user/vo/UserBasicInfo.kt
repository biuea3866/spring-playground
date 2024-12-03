package com.biuea.tablereservingapplication.domain.user.vo

import com.biuea.tablereservingapplication.domain.user.aggregate.UserGrade

data class UserBasicInfo(
    val nickname: String,
    val email: String,
    val phoneNumber: String,
    val profileImageUrl: String?,
    val grade: UserGrade
) {
}