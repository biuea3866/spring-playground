package com.biuea.objectpractice.ticket

class TicketSeller(
    private var _ticketOffice: TicketOffice?
) {
    val ticketOffice: TicketOffice get() = this._ticketOffice
        ?: throw IllegalStateException("ticketOffice is null")

    fun mapBy(ticketOffice: TicketOffice) {
        this._ticketOffice = ticketOffice
    }

    // 소극장에서의 enter 로직을 이관받아 주체적으로 ~에게 티켓을 팔 것인지 정의한다.
    // 그리고 관람객이 티켓을 구매하는 것은 Audience의 역할이므로 Audience에게 위임한다.
    fun sellTo(audience: Audience) {
        this.ticketOffice.sellTicketTo(audience)
    }
}