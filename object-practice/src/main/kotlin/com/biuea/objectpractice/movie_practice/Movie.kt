package com.biuea.objectpractice.movie_practice

import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDateTime

internal class Movie private constructor(
    private var _title: String,
    private var _runningTime: Duration,
    private var _fee: Money,
    private var discountPolicy: DiscountPolicy
) {
    val fee get() = this._fee

    /**
     * discountPolicy에 calculateDiscountAmount 메서드를 호출하여 할인 금액을 계산한다.
     */
    fun calculateMovieFee(screening: Screening): Money {
        return this.fee.minus(discountPolicy.calculateDiscountAmount(screening))
    }

    companion object {
        fun create(
            title: String,
            runningTime: Duration,
            fee: Money,
            discountPolicy: DiscountPolicy
        ): Movie {
            return Movie(title, runningTime, fee, discountPolicy)
        }
    }
}

internal abstract class DiscountPolicy(
    private val _discountConditions: MutableSet<DiscountCondition>
) {
    fun addDiscountConditions(discountConditions: Set<DiscountCondition>) {
        _discountConditions.addAll(discountConditions)
    }

    fun calculateDiscountAmount(screening: Screening): Money {
        for (discountCondition in _discountConditions) {
            if (discountCondition.isSatisfiedBy(screening)) {
                return getDiscountAmount(screening)
            }
        }

        return Money.ZERO
    }

    protected abstract fun getDiscountAmount(screening: Screening): Money
}

internal interface DiscountCondition {
    fun isSatisfiedBy(screening: Screening): Boolean
}

internal class SequenceCondition(
    private val sequence: Int
) : DiscountCondition {
    override fun isSatisfiedBy(screening: Screening): Boolean {
        return screening.isSequence(sequence)
    }
}

internal class PeriodCondition(
    private val dayOfWeek: DayOfWeek,
    private val startTime: LocalDateTime,
    private val endTime: LocalDateTime
) : DiscountCondition {
    override fun isSatisfiedBy(screening: Screening): Boolean {
        return screening.startTime.dayOfWeek == dayOfWeek &&
            startTime <= screening.startTime &&
            endTime >= screening.startTime
    }
}

internal class AmountDiscountPolicy(
    private val discountAmount: Money,
    discountConditions: MutableSet<DiscountCondition>
) : DiscountPolicy(discountConditions) {
    override fun getDiscountAmount(screening: Screening): Money {
        return discountAmount
    }
}

internal class PercentDiscountPolicy(
    private val percent: Double,
    discountConditions: MutableSet<DiscountCondition>
) : DiscountPolicy(discountConditions) {
    override fun getDiscountAmount(screening: Screening): Money {
        return screening.movieFee.times(percent)
    }
}