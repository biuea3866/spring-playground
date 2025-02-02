package com.biuea.table.domain.restaurant

interface WaitingNumberStrategy {
    fun issue(): Int
}

class ConfirmWaitingNumberStrategy(
    val isRemainTable: Boolean,
    val notEntranceCustomers: Int
) : WaitingNumberStrategy {
    override fun issue(): Int {
        return if (isRemainTable) {
            0
        } else {
            notEntranceCustomers + 1
        }
    }
}

class RequestWaitingNumberStrategy: WaitingNumberStrategy {
    override fun issue(): Int {
        return -1
    }
}