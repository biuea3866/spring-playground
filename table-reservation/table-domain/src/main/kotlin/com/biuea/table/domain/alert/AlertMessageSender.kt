package com.biuea.table.domain.alert

interface AlertMessageSender {
    fun send(message: AlertMessage)
}