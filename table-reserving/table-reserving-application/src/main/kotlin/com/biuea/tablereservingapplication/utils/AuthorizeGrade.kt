package com.biuea.tablereservingapplication.utils

import com.biuea.tablereservingapplication.domain.user.aggregate.UserGrade

annotation class AuthorizeGrade(
    val grade: UserGrade
)