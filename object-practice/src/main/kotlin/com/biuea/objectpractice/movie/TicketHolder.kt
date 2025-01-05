package com.biuea.objectpractice.movie

class TicketHolder private constructor(
    val _userId: Long
) {
    val userId: Long get() = _userId

    companion object {
        fun create(userId: Long): TicketHolder {
            return TicketHolder(userId)
        }
    }
}