package com.biuea.objectpractice.movie.movie

/**
 * 영화
 */
class Movie private constructor(
    private var _title: String,
    private var _runningTime: Long,
    private var _fee: Long,
    private var _discountPolicy: MovieDiscountPolicyFactory,
    private var _rank: Int
) {
    val title: String get() = _title
    val fee get() = _fee
    val rank get() = _rank

    fun calculate(): Long {
        this._discountPolicy.createPercentDiscountPolicy(0.1)
        return _fee - _discountPolicy.calculate()
    }

    fun setMovieDiscountPolicy() {
        _discountPolicy = MovieDiscountPolicyFactory.createAmountDiscountPolicy(1000)
    }

    companion object {
        fun create(
            title: String,
            runningTime: Long,
            fee: Long,
            discountPolicy: DiscountPolicy
        ): Movie {
            return Movie(title, runningTime, fee, discountPolicy)
        }
    }
}