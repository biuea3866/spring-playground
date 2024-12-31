package com.biuea.objectpractice.movie.ticket_holder

class TicketHolder private constructor(
    val _userId: Long
) {
    val userId: Long get() = _userId

    fun reserve(): Ticket {
        return Ticket.create(this)
    }

    companion object {
        fun create(userId: Long): TicketHolder {
            return TicketHolder(userId)
        }
    }
}