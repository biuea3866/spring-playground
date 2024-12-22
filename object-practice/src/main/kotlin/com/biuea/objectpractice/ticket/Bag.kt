package com.biuea.objectpractice.ticket

class Bag private constructor(
    private var amount: Long,
    private var invitation: Invitation?,
    private var ticket: Ticket?
) {
    fun mapBy(ticket: Ticket) {
        this.ticket = ticket
    }

    fun hasInvitation(): Boolean {
        return this.invitation != null
    }

    fun hasTicket(): Boolean {
        return this.ticket != null
    }

    fun plus(amount: Long) {
        this.amount += amount
    }

    fun minus(amount: Long) {
        this.amount -= amount
    }

    companion object {
        fun create(amount: Long): Bag {
            return Bag(
                amount = amount,
                invitation = null,
                ticket = null
            )
        }

        fun create(
            invitation: Invitation,
            amount: Long
        ): Bag {
            return Bag(
                invitation = invitation,
                amount = amount,
                ticket = null
            )
        }
    }
}