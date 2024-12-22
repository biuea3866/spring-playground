package com.biuea.designpattern

// private constructor를 사용하여 생성자를 제한하여 외부에서 생성자를 호출하지 못하도록 한다.
class UserEntity private constructor(
    val id: Long,
    val email: String,
    val password: String,
) {
    fun isValidEmail(validator: Validator): Boolean {
        return validator.isValid(this.email)
    }

    companion object {
        fun create(
            email: String,
            password: String
        ): UserEntity {
            return UserEntity(0L, email, password)
        }

        fun fromBy(
            id: Long,
            email: String,
            password: String
        ): UserEntity {
            return UserEntity(id, email, password)
        }
    }
}

data class UserMapper(
    val id: Long,
    val email: String,
    val password: String
) {
    fun toDomain(): UserEntity {
        return UserEntity.fromBy(this.id, this.email, this.password)
    }

    companion object {
        fun fromBy(domain: UserEntity): UserMapper {
            return UserMapper(domain.id, domain.email, domain.password)
        }
    }
}

// jpa entity는 코틀린에서 open이기 때문에 private을 사용할 수 없다
class UserJpaEntity(
    val id: Long,
    val email: String,
    val password: String
) {
    fun toDomain(): UserEntity {
        return UserEntity.fromBy(this.id, this.email, this.password)
    }

    companion object {
        // domain으로부터 jpa entity를 생성할 때 Private 문제를 어떻게 해결할 것인가..
        // getter?: 도메인 객체에서 getter를 사용하여 값을 가져올 수 있도록 한다.
        // builder? 도메인 객체를 의존하는 복사본 형태의 builder를 사용하여 값을 가져올 수 있도록 한다.
        // -> 그냥 dto를 만들어서 거기서 의존하면 되지않나?
        // factory?
        fun fromBy(domain: UserEntity): UserJpaEntity {
            return UserJpaEntity(domain.id, domain.email, domain.password)
        }
    }
}

// fun interface를 사용하여 함수형 인터페이스를 정의한다.
fun interface Validator {
    fun isValid(email: String): Boolean
}

object YahooValidator: Validator {
    override fun isValid(email: String): Boolean {
        return email.split("@").last() == "yahoo.com"
    }
}

object GmailValidator: Validator {
    override fun isValid(email: String): Boolean {
        return email.split("@").last() == "gmail.com"
    }
}

fun main() {
    val user1 = UserEntity.create("test@yahoo.com", "password")
    user1.isValidEmail(YahooValidator)
    val user2 = UserEntity.create("test@gmail.com", "password")
    user2.isValidEmail(GmailValidator)
}

