package com.biuea.objectpractice.movie.movie

import com.biuea.objectpractice.movie.screening.Screening
import com.biuea.objectpractice.movie_practice.DiscountPolicy

/**
 * 영화
 */
class Movie<T: MovieDiscountPolicy> private constructor(
    private var _title: String,
    private var _runningTime: Long,
    private var _fee: Long,
    private var _rank: Int,
    val discountPolicy: T
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
        fun<T: MovieDiscountPolicy> create(
            title: String,
            runningTime: Long,
            fee: Long,
            discountPolicy: T
        ): Movie<T> {
            return Movie(title, runningTime, fee, -1, discountPolicy)
        }
    }
}