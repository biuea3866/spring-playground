package com.biuea.designpattern.factorymethod

import kotlin.coroutines.coroutineContext
import kotlin.coroutines.suspendCoroutine
import kotlin.reflect.KClass
import kotlin.reflect.KFunction

interface Test

// Test 클래스를 구현하기 위해선 int 2개의 인자를 받아야 함 (팀 내부에서 약속)
class Test1(val a: Int, b: Int): Test
class Test2(val a: Int, b: Int): Test

// KFunction2<Int, Int, Test>
var factory = ::Test1

// KClass<out Test> to KFunction2<Int, Int, Test>
val router = hashMapOf(
    Test1::class to ::Test1,
    Test2::class to ::Test2
)

// inline, refied를 이용해서 제네릭 T를 런타임에서 어떤 형인지 추론이 가능
inline fun <reified T: Test> factory1(a: Int, b: Int) = router[T::class]?.invoke(a, b)

val test1 = factory1<Test1>(1, 2)

// Facade를 팩토리 메서드로 구현
interface Facade

interface FacadeDTO

class TestFacade(dto: TestFacadeDTO): Facade

class TestFacadeDTO: FacadeDTO

class AFacade(dto: AFacadeDTO): Facade

class AFacadeDTO: FacadeDTO

val AFacadeFactory = ::AFacade
val TestFacadeFactory = ::TestFacade

val factoryRouter = hashMapOf(
    AFacadeDTO::class to AFacadeFactory,
    TestFacadeDTO::class to TestFacadeFactory
)

// 정적 팩토리 메서드
// 코틀린의 람다는 interface -> operator fun invoke()를 가지고 있음
// 람다를 상속받는 경우 invoke()를 구현해야 함
fun interface Factories<T: Any>: () -> T

// internal: 모듈 단위에서 사용하는 접근 제한자
// @PublishedApi: internal에서만 되지만 public inline으로는 사용 가능
// companion object를 사용하면
class NoArgFactory<T: Any> @PublishedApi internal constructor(
    val type: KClass<T>,
    val factory: () -> T
): Factories<T> {
    companion object {
        inline operator fun <reified T: Any> invoke(
            noinline factory: () -> T
        ): Factories<T> {
//            someProcess()
            return NoArgFactory(T::class, factory)
        }
    }

    override inline fun invoke(): T = factory()
}

class Member

val memberFactory = NoArgFactory(::Member)

suspend fun getMember(): Member = memberFactory()