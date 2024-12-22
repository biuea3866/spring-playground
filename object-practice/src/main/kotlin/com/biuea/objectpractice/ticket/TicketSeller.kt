package com.biuea.objectpractice.ticket

class TicketSeller(
    private var _ticketOffice: TicketOffice?
) {
    val ticketOffice: TicketOffice get() = this._ticketOffice
        ?: throw IllegalStateException("ticketOffice is null")

    fun mapBy(ticketOffice: TicketOffice) {
        this._ticketOffice = ticketOffice
    }
}