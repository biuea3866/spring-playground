package com.biuea.designpattern

/**
 * 전략 패턴은 런타임 중 객체의 동작을 동적으로 바뀌게 하는 패턴이다.
 *
 * * 전략 알고리즘 객채: 알고리즘, 행위, 동작을 객체로 정의한 구현체
 * * 전략 인터페이스: 모든 전략 구현체에 대한 공용 인터페이스
 * * 컨텍스트: 알고리즘을 실행할 때마다 전략을 바꿀 수 있는 환경
 * * 클라이언트: 전략을 사용하는 객체
 *
 * 1. 동일 계열의 알고리즘군을 정의하고 -> 전략 구현체
 * 2. 각각의 알고리즘을 캡슐화하여 -> 인터페이스로 추상화
 * 3. 이들을 상호 교환이 가능하도록 만든다. -> 합성으로 구성
 * 4. 알고리즘을 사용하는 클라이언트와 상관없이 독립적으로 -> 컨텍스트 수정없이
 * 5. 알고리즘을 다양하게 변경할 수 있게 한다. -> 메소드를 통해 전략 객체를 실시간으로 변경
 */

interface Strategy {
    fun execute()
}

class ConcreateStrategyA(
    private val strategyADTO: StrategyADTO
): Strategy {
    override fun execute() {
        println("Strategy A")
    }

    class StrategyADTO(
        val grade: String
    )
}

class ConcreateStrategyB: Strategy {
    override fun execute() {
        println("Strategy B")
    }
}

class Context(
    var strategy: Strategy
): Strategy {
    override fun execute() {
        strategy.execute()
    }
}

fun main() {
    val dto = ConcreateStrategyA.StrategyADTO("A")
    val context = Context(ConcreateStrategyA(dto))
    context.execute()

    context.strategy = ConcreateStrategyB()
    context.execute()
}