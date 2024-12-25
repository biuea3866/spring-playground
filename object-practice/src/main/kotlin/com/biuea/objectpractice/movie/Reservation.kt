package com.biuea.objectpractice.movie

class Reservation private constructor(
    private var _screening: Screening,
    private var _ticketHolder: TicketHolder,
    private var _originalFee: Long,
    private var _fee: Long,
    private var _title: String
) {
    val screening get() = _screening
    val ticketHolder get() = _ticketHolder
    val originalFee get() = _originalFee
    val fee get() = _fee
    val title get() = _title

    companion object {
        fun create(
            screening: Screening,
            ticketHolder: TicketHolder,
            originalFee: Long,
            fee: Long,
            title: String,
        ): Reservation {
            return Reservation(screening, ticketHolder, originalFee, fee, title)
        }
    }
}