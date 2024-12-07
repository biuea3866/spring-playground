package com.biuea.designpattern

/**
 * 팩토리 메서드 패턴은 객체 생성 시 어떤 클래스의 인스턴스를 만들 떄 서브 클래스에서 결정 짓도록 한다.
 * 인스턴스 생성을 서브 클래스에게 위임하여 슈퍼 클래스는 인터페이스를 의존하고, 실제 어떤 구현 클래스를 호출할지는 서브 클래스에서 구현한다.
 */

interface User {
    fun signup()

    interface SignupDTO
}

class PlatformUser(
    private val signupDTO: User.SignupDTO
): User {
    override fun signup() {
        println("PlatformUser signup")
    }

    class PlatformUserSignupDTO(
        val grade: String
    ) : User.SignupDTO
}

class AdminUser {
    fun signup(signupDTO: User.SignupDTO) {
        TODO("Not yet implemented")
    }

    class AdminUserSignupDTO : User.SignupDTO
}

class RestaurantUser {
    fun signup(signupDTO: User.SignupDTO) {
        TODO("Not yet implemented")
    }

    class RestaurantUserSignupDTO : User.SignupDTO
}

abstract class UserFactory {
    fun newInstance(dto: User.SignupDTO): User {
        val user = createUser()
        user.signup()
        return user
    }

    protected abstract fun createUser(): User
}

class PlatformUserFactory(
    private val dto: User.SignupDTO
): UserFactory() {
    override fun createUser(): User {
        return PlatformUser(this.dto)
    }
}

fun main() {
    val platformUserFactory = PlatformUserFactory()
    val platformUser = platformUserFactory.newInstance(PlatformUser.PlatformUserSignupDTO(grade = "VIP"))
    platformUser.signup()
}