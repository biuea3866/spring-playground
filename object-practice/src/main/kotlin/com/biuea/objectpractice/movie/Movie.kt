package com.biuea.objectpractice.movie

/**
 * 영화
 */
class Movie private constructor(
    private var _title: String,
    private var _runningTime: Long,
    private var _fee: Long,
    private var _discountPolicy: DiscountPolicy
) {
    val title: String get() = _title
    val fee get() = _fee

    fun calculate(): Long {
        return _fee - _discountPolicy.calculate()
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

class DiscountPolicyFactory {
    fun createAmountDiscountPolicy(discountAmount: Long): DiscountPolicy {
        return AmountDiscountPolicy(discountAmount)
    }

    fun createPercentDiscountPolicy(percent: Double): DiscountPolicy {
        return PercentDiscountPolicy(percent)
    }
}

abstract class DiscountPolicy {
    private val discountConditions: MutableSet<DiscountCondition> = mutableSetOf()

    fun addDiscountCondition(discountCondition: DiscountCondition) {
        discountConditions.add(discountCondition)
    }

    fun removeDiscountCondition(discountCondition: DiscountCondition) {
        discountConditions.remove(discountCondition)
    }

    abstract fun calculate(): Long
}

class AmountDiscountPolicy(
    val discountAmount: Long
) : DiscountPolicy() {
    override fun calculate(): Long {

    }
}

class PercentDiscountPolicy(
    val _percent: Double
) : DiscountPolicy {
    fun changePercent(percent: Double) {
        _percent = percent
    }

    override fun calculate(): Long {

    }
}

interface DiscountCondition

class SequenceDiscountCondition private constructor(
    private var _sequence: Int
) : DiscountCondition {
    fun changeSequence(sequence: Int) {
        _sequence = sequence
    }
}

class PeriodDiscountCondition private constructor(
    private var _dayOfWeek: Int,
    private var _startTime: Int,
    private var _endTime: Int
) : DiscountCondition {
    fun changeDayOfWeek(dayOfWeek: Int) {
        _dayOfWeek = dayOfWeek
    }

    fun changeStartTime(startTime: Int) {
        _startTime = startTime
    }

    fun changeEndTime(endTime: Int) {
        _endTime = endTime
    }
}