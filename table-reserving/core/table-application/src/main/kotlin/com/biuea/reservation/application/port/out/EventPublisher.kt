package com.biuea.reservation.application.port.out

abstract class EventPublisher {
    fun execute(event: Event) {
        publish(event)
    }

    protected abstract fun publish(event: Event)
}

interface Event {
    val id: Long
}