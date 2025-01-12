package com.biuea.tablereservingapplication.domain.user.repository

import com.biuea.tablereservingapplication.domain.user.aggregate.UserAggregate

interface UserRepository {
    fun findByEmail(email: String): UserAggregate?
    fun findById(userId: Long): UserAggregate?
}