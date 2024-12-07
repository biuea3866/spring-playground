package com.biuea.designpattern

/**
 * 어댑터 패턴
 *
 * 호환성이 없는 인터페이스 때문에 함꼐 동작할 수 없는 클래스들을 함께 동작하도록 변환
 *
 * 프로그램의 기본 비즈니스 로직에서 인터페이스를 분리할 수 있기 때문에 SRP를 만족한다.
 * 기존 코드를 건드리지 않고 클라이언트 인터페이스를 어댑터에 연결시키기 때문에 OCP를 만족한다.
 */

// 예시1
interface Port {
    fun getService()
}

// 클라이언트에서 사용하고자 하는 서비스
class Service {
    fun execute() {
        println("Service execute")
    }
}

class Adaptor: Port {
    private val service = Service()

    override fun getService() {
        service.execute()
    }
}

fun main() {
    val adaptor = Adaptor()
    adaptor.getService()
}

// 예시2
// 기존 연동중인 타 시스템
interface SomeIntegrationClient {
    @POST("/api/v1/some-integration")
    fun transferData(data: Int): Any
}

interface ChangeIntegrationClient {
    @POST("/api/v1/change-integration")
    fun transferData(data: Int): Any
}

class IntegrationService(
    private val someIntegrationClient: SomeIntegrationClient,
    private val changeIntegrationClient: ChangeIntegrationClient
) {
    fun execute(data: Int) {
        someIntegrationClient.transferData(data)
    }
}

fun main2() {
    val integrationService = IntegrationService()
}