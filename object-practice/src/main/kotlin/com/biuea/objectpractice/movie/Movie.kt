package com.biuea.objectpractice.movie

/**
 * 영화
 */
class Movie<T: MovieDiscountPolicy> private constructor(
    private var _title: String,
    private var _runningTime: Long,
    private var _fee: Money,
    private var _rank: Int,
    val discountPolicy: T
) {
    val title: String get() = _title
    val fee get() = _fee
    val rank get() = _rank

    fun calculate(
        screening: Screening,
        count: Int
    ): Money {
        return this.discountPolicy.calculate(screening, count, fee)
    }

    companion object {
        fun<T: MovieDiscountPolicy> create(
            title: String,
            runningTime: Long,
            fee: Money,
            discountPolicy: T
        ): Movie<T> {
            return Movie(title, runningTime, fee, -1, discountPolicy)
        }
    }
}