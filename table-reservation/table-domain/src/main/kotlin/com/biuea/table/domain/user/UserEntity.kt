package com.biuea.table.domain.user

import jakarta.persistence.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Entity
@Table(name = "user")
class UserEntity(
    @Column(name = "email")
    val email: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "nickname")
    val nickname: String,

    @Column(name = "phone_number")
    val phoneNumber: String,

    @Embedded
    val profileInfo: UserProfileInfo?,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    val status: UserStatus,

    @Column(name = "grade")
    @Enumerated(EnumType.STRING)
    val grade: UserGrade
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    fun changePassword(password: String) {
        this.password = password
    }

    companion object {
        fun create(
            email: String,
            password: String,
            nickname: String,
            phoneNumber: String,
            s3Key: String?,
            s3Bucket: String?,
            filename: String?,
            status: UserStatus = UserStatus.ACTIVE,
            grade: UserGrade = UserGrade.BRONZE
        ): UserEntity {
            return UserEntity(
                email = email,
                password = password,
                nickname = nickname,
                phoneNumber = phoneNumber,
                profileInfo = when {
                    s3Key != null && s3Bucket != null && filename != null -> UserProfileInfo(
                        s3Key = s3Key,
                        s3Bucket = s3Bucket,
                        filename = filename
                    )
                    else -> null
                },
                status = status,
                grade = grade
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
    ;

    fun isGreaterThan(grade: UserGrade): Boolean {
        return this.ordinal > grade.ordinal
    }
}

@Embeddable
data class UserProfileInfo(
    @Column(name = "s3_key")
    val s3Key: String,
    @Column(name = "s3_bucket")
    val s3Bucket: String,
    @Column(name = "filename")
    val filename: String
)