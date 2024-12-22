package com.biuea.objectpractice.ticket

class Audience private constructor(
    private var _bag: Bag?
) {
    val bag: Bag get() = this._bag
        ?: throw IllegalStateException("Bag is null")

    fun buy(ticket: Ticket): Long {
        if (bag.hasInvitation()) {
            bag.mapBy(ticket)
            return 0L
        } else {
            bag.minus(ticket.fee)
            return ticket.fee
        }
    }

    companion object {
        fun create(bag: Bag): Audience {
            return Audience(bag)
        }
    }
}