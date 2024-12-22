package com.biuea.objectpractice.ticket

class Theater private constructor(
    private val ticketSeller: TicketSeller
) {
    fun enter(audience: Audience) {
        if (audience.bag.hasInvitation()) {
            val ticket = ticketSeller.ticketOffice.getTicket()
            audience.bag.mapBy(ticket)
        } else {
            val ticket = ticketSeller.ticketOffice.getTicket()
            audience.bag.minus(ticket.fee)
            ticketSeller.ticketOffice.plusAmount(ticket.fee)
            audience.bag.mapBy(ticket)
        }
    }

    companion object {
        fun create(ticketSeller: TicketSeller): Theater {
            return Theater(ticketSeller)
        }
    }
}