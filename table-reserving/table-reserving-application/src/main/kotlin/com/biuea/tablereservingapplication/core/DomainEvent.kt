package com.biuea.tablereservingapplication.core

import java.time.ZonedDateTime

/**
 * 도메인 이벤트를 나타내는 추상 클래스
 * 해당 추상 클래스를 상속받아 이벤트를 구현
 * @param occurredAt: 이벤트가 발생한 시간
 * @param event: 이벤트의 이름 (큐의 토픽명으로도 활용)
 * @param payload: 이벤트의 페이로드
 */
abstract class DomainEvent(
    open val occurredAt: ZonedDateTime,
    open val event: String,
    open val payload: Any,
)