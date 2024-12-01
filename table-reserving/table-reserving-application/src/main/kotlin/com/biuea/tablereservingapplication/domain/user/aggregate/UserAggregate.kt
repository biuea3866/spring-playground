package com.biuea.tablereservingapplication.domain.user.aggregate

import com.biuea.tablereservingapplication.core.Id
import java.time.ZonedDateTime

class UserAggregate private constructor(
    private val _id: Id,
    private val _email: String,
    private val _nickname: String,
    private val _password: String,
    private val _status: UserStatus,
    private val _grade: UserGrade,
    private val _createdAt: ZonedDateTime,
    private val _updatedAt: ZonedDateTime,
    private val _deletedAt: ZonedDateTime?
) {
    val id get() = _id
    val email get() = _email
    val nickname get() = _nickname
    val password get() = _password
    val status get() = _status
    val grade get() = _grade

    companion object {
        fun create(
            id: Id,
            email: String,
            nickname: String,
            password: String,
            status: UserStatus,
            grade: UserGrade,
            createdAt: ZonedDateTime,
            updatedAt: ZonedDateTime,
            deletedAt: ZonedDateTime?
        ): UserAggregate {
            return UserAggregate(
                _id = id,
                _email = email,
                _nickname = nickname,
                _password = password,
                _status = status,
                _grade = grade,
                _createdAt = createdAt,
                _updatedAt = updatedAt,
                _deletedAt = deletedAt
            )
        }
    }
}

enum class UserStatus {
    ACTIVE,
    INACTIVE
}

enum class UserGrade {
    NORMAL,
    VIP
}