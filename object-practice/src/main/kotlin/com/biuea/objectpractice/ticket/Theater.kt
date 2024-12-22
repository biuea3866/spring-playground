package com.biuea.objectpractice.ticket

class Theater private constructor(
    private val ticketSeller: TicketSeller
) {
    // ticket seller에게 입장 로직을 위임한다.
    fun enter(audience: Audience) {
        ticketSeller.sellTo(audience)
    }

    companion object {
        fun create(ticketSeller: TicketSeller): Theater {
            return Theater(ticketSeller)
        }
    }
}