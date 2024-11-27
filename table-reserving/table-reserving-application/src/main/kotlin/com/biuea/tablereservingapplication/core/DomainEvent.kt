package com.biuea.tablereservingapplication.core

import com.biuea.tablereservingapplication.domain.restaurant.event.RestaurantEventType
import java.time.ZonedDateTime

/**
 * 도메인 이벤트를 나타내는 추상 클래스
 * 해당 추상 클래스를 상속받아 이벤트를 구현
 * @param occurredAt: 이벤트가 발생한 시간
 * @param event: 이벤트의 이름 (큐의 토픽명으로도 활용)
 * @param payload: 이벤트의 페이로드
 */
abstract class DomainEvent(
    val occurredAt: ZonedDateTime,
    val event: String,
    val payload: DomainEventPayload
) {
    companion object{
        fun build(
            occurredAt: ZonedDateTime = ZonedDateTime.now(),
            payload: DomainEventPayload,
            event: String
        ): DomainEvent {
            return object: DomainEvent(
                occurredAt = occurredAt,
                event = event,
                payload = payload
            ){}
        }
    }
}

interface DomainEventPayload