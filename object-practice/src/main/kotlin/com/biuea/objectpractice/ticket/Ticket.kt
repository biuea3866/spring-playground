package com.biuea.objectpractice.ticket

class Ticket(
    private val _fee: Long
) {
    val fee: Long get() = this._fee
}