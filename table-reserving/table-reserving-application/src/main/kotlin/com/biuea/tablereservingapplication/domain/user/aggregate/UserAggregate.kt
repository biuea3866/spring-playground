package com.biuea.tablereservingapplication.domain.user.aggregate

import com.biuea.tablereservingapplication.core.DomainEvent
import com.biuea.tablereservingapplication.core.Id
import com.biuea.tablereservingapplication.domain.user.vo.AuthenticationInfo
import com.biuea.tablereservingapplication.domain.user.vo.UserBasicInfo
import java.time.ZonedDateTime

class UserAggregate private constructor(
    private val _id: Id,
    private val _nickname: String,
    private val _email: String,
    private val _password: String,
    private val _phoneNumber: String,
    private val _profileImageUrl: String?,
    private val _status: UserStatus,
    private val _grade: UserGrade,
    private val _createdAt: ZonedDateTime,
    private val _updatedAt: ZonedDateTime,
    private val _deletedAt: ZonedDateTime?
) {
    val id: Id get() = this._id
    val basicInfo: UserBasicInfo get() = UserBasicInfo(
        nickname = this._nickname,
        email = this._email,
        phoneNumber = this._phoneNumber,
        profileImageUrl = this._profileImageUrl,
        grade = this._grade
    )
    val authenticationInfo: AuthenticationInfo get() = AuthenticationInfo(
        email = this._email,
        password = this._password
    )

    fun validatePassword(matchPassword: (password: String) -> Unit) {
        this.authenticationInfo.validatePassword { matchPassword(it) }
    }

    fun signup(publish: (DomainEvent) -> Unit): UserAggregate {
        require(this._nickname.isNotEmpty()) { "닉네임은 필수입니다." }
        require(this._email.isNotEmpty()) { "이메일은 필수입니다." }
        require(this._password.isNotEmpty()) { "비밀번호는 필수입니다." }
        require(this._phoneNumber.isNotEmpty()) { "전화번호는 필수입니다." }
        require(this._nickname.length >= 2) { "닉네임은 2자 이상이어야 합니다." }

//        publish()

        return this
    }

    fun updateProfile() {}

    companion object {
        fun create(
            nickname: String,
            email: String,
            password: String,
            phoneNumber: String,
        ): UserAggregate {
            return UserAggregate(
                _id = Id(0L),
                _nickname = nickname,
                _email = email,
                _password = password,
                _phoneNumber = phoneNumber,
                _profileImageUrl = null,
                _status = UserStatus.ACTIVE,
                _grade = UserGrade.BRONZE,
                _createdAt = ZonedDateTime.now(),
                _updatedAt = ZonedDateTime.now(),
                _deletedAt = null
            )
        }
    }
}

enum class UserStatus {
    ACTIVE,
    INACTIVE
}

enum class UserGrade {
    BRONZE,
    SILVER,
    GOLD,
    PLATINUM,
    DIAMOND
}