package com.biuea.designpattern

import org.springframework.context.ApplicationEventPublisher

/**
 * 구체적인 기능을 클래스에 구현하는 것이 아니라 실행될 기능을 캡슐화하여 이를 활용한다.
 */
fun interface Publisher {
    fun publish(event: DomainEvent)
}

interface DomainEvent

class UserRegisteredEvent: DomainEvent

class KafkaPublisher(
    private val kafkaTemplate: KafkaTemplate
): Publisher {
    override fun publish(event: DomainEvent) {
        kafkaTemplate.send(event)
    }

    class KafkaTemplate {
        fun send(event: DomainEvent) {
            println("KafkaTemplate send")
        }
    }
}

class RabbitMQPublisher(
    private val rabbitMQTemplate: RabbitMQTemplate
): Publisher {
    override fun publish(event: DomainEvent) {
        rabbitMQTemplate.send(event)
    }

    class RabbitMQTemplate {
        fun send(event: DomainEvent) {
            println("RabbitMQTemplate send")
        }
    }
}

class InternalEventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher
): Publisher {
    override fun publish(event: DomainEvent) {
        this.applicationEventPublisher.publishEvent(event)
    }
}

class EventPublisher(
    val event: DomainEvent
) {
    fun publish(publisher: Publisher) {
        publisher.publish(event)
    }
}

class SomeDomainService(
    private val eventPublisher: EventPublisher
) {
    fun doSomething() {
        val publisher = KafkaPublisher(KafkaPublisher.KafkaTemplate())
    }
}