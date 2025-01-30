package com.biuea.table.application.authentication

import com.biuea.table.domain.user.UserEntity
import com.biuea.table.domain.user.UserRepository
import com.biuea.table.domain.user.authentication.AuthenticationService
import jakarta.validation.constraints.Size
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SignUpApplication(
    private val userRepository: UserRepository,
    private val authenticationService: AuthenticationService
) {
    @Transactional
    fun signUp(command: SignUpCommand): UserEntity {
        val user = command.toEntity()
        // 1. 회원가입을 위한 유효성 검사
        // 2. 비밀번호 암호화
        val encryptPassword = authenticationService.encodePassword(user)
        user.changePassword(encryptPassword)
        // 3. 회원가입
        return userRepository.save(user)
        // TODO: 4. s3 프로필 이미지 업로드
        // TODO: 5. 알림 추가
    }
}

data class SignUpCommand(
    @Size(min = 1, max = 50, message = "이메일은 1자 이상 50자 이하로 입력해주세요.")
    // TODO: 이메일 형식 검증
    val email: String,
    @Size(min = 8, max = 50, message = "비밀번호는 8자 이상 50자 이하로 입력해주세요.")
    // TODO: 비밀번호 형식 검증
    val password: String,
    @Size(min = 1, max = 50, message = "닉네임은 1자 이상 50자 이하로 입력해주세요.")
    val nickname: String,
    @Size(min = 10, max = 20, message = "전화번호는 10자 이상 20자 이하로 입력해주세요.")
    val phoneNumber: String,
    val s3Key: String?,
    val s3Bucket: String?,
    val filename: String?
) {
    fun toEntity(): UserEntity {
        return UserEntity.create(
            email = email,
            password = password,
            nickname = nickname,
            phoneNumber = phoneNumber,
            s3Key = s3Key,
            s3Bucket = s3Bucket,
            filename = filename
        )
    }
}