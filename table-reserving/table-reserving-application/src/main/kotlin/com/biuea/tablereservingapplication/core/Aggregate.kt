package com.biuea.tablereservingapplication.core

abstract class Aggregate {
    private val domainEvents: Set<DomainEvent> = mutableSetOf()

    fun addDomainEvents(domainEvents: Set<DomainEvent>) {
        this.domainEvents.toMutableSet().addAll(domainEvents)
    }

    fun pullEvents(): Set<DomainEvent> {
        val domainEvents = this.domainEvents
        this.domainEvents.toMutableSet().clear()
        return domainEvents
    }
}