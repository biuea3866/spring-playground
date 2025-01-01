package com.biuea.objectpractice.movie_practice

import java.time.LocalDateTime

internal class Screening private constructor(
    private var _movie: Movie,
    private var _sequence: Int,
    private var _whenScreened: LocalDateTime
) {
    val startTime get() = _whenScreened
    val movieFee get() = _movie.fee

    fun isSequence(sequence: Int): Boolean {
        return this._sequence == sequence
    }

    fun reserve(
        customer: Customer,
        audienceCount: Int
    ): Reservation {
        return Reservation.create(customer, this, calculateFee(audienceCount), audienceCount)
    }

    fun calculateFee(audienceCount: Int): Money {
        return this._movie.calculateMovieFee(this).times(audienceCount)
    }
}