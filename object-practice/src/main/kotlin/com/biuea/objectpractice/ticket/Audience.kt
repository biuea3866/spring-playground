package com.biuea.objectpractice.ticket

class Audience private constructor(
    private var _bag: Bag?
) {
    val bag: Bag get() = this._bag
        ?: throw IllegalStateException("Bag is null")

    companion object {
        fun create(bag: Bag): Audience {
            return Audience(bag)
        }
    }
}