package com.biuea.objectpractice.movie.movie

import com.biuea.objectpractice.movie.screening.Screening

/**
 * 영화
 */
class Movie private constructor(
    private var _title: String,
    private var _runningTime: Long,
    private var _fee: Long,
    private var _rank: Int,
    val discountPolicy: MovieDiscountPolicy?
) {
    val title: String get() = _title
    val fee get() = _fee
    val rank get() = _rank

    fun calculate(screening: Screening): Long {
        if (discountPolicy == null) return this.fee

        when (this.discountPolicy) {
            is PercentDiscountPolicy -> {
                return this.discountPolicy.calculate(screening)
            }
            is AmountDiscountPolicy -> {
                return this.discountPolicy.calculate(screening)
            }
            else -> {
                return this.fee
            }
        }
    }

    companion object {
        fun create(
            title: String,
            runningTime: Long,
            fee: Long,
        ): Movie {
            return Movie(title, runningTime, fee, -1, null)
        }
    }
}