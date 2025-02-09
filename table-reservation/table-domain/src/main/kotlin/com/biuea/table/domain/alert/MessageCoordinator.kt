package com.biuea.table.domain.alert

import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
class MessageCoordinator(
    private val messageSender: AlertMessageSender
) {
    private val messageBuilders: MutableSet<AlertMessageBuilder> = mutableSetOf()

    fun addMessageBuilders(messageBuilders: Set<AlertMessageBuilder>) {
        this.messageBuilders.addAll(messageBuilders)
    }

    fun send() {
        this.messageBuilders.forEach { messageSender.send(it.build()) }
    }
}