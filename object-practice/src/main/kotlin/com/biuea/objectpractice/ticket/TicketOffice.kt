package com.biuea.objectpractice.ticket

class TicketOffice private constructor(
    private var amount: Long,
    private val tickets: MutableList<Ticket> = mutableListOf()
) {
    fun getTicket(): Ticket {
        return this.tickets.removeAt(0)
    }

    fun minusAmount(amount: Long) {
        this.amount -= amount
    }

    fun plusAmount(amount: Long) {
        this.amount += amount
    }

    companion object {
        fun create(
            amount: Long,
            tickets: MutableList<Ticket>
        ): TicketOffice {
            return TicketOffice(amount, tickets)
        }
    }
}