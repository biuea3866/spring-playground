package com.biuea.designpattern

import org.slf4j.LoggerFactory

/**
 * 템플릿 메서드는 코드의 중복을 없애기 위한 패턴이다.
 * 추상 클래스는 템플릿을 제공하고, 서브 클래스가 구체적인 로직을 작성
 * AOP와 같은 역할
 */

abstract class Logging {
    val logger = LoggerFactory.getLogger(this::class.java)

    init {
        this.measure()
    }

    private fun measure() {
        logger.info("start")
        val start = System.currentTimeMillis()
        execute()
        val end = System.currentTimeMillis()
        logger.info("end")
        logger.info("execution time: ${end - start}ms")
    }

    protected abstract fun execute()
}

class CreateUserService: Logging() {
    fun createUser() {
        logger.info("some logic")
    }

    override fun execute() {
        logger.info("CreateUserService execute")
    }
}

fun main() {
    /**
     * 템플릿 메서드 패턴을 사용하여 로깅을 구현
     */
    val createUserService = CreateUserService()
    createUserService.createUser()
}