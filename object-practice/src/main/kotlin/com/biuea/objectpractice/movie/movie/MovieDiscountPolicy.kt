package com.biuea.objectpractice.movie.movie

import java.time.ZonedDateTime

enum class MovieDiscountPolicyType {
    AMOUNT,
    PERCENT
}

enum class DiscountConditionType {
    SEQUENCE,
    PERIOD
}

class MovieDiscountPolicyFactory {
    fun createAmountDiscountPolicy(amount: Long): MovieDiscountPolicy {
        return SequenceDiscountPolicy(amount)
    }

    fun createPercentDiscountPolicy(percent: Double): MovieDiscountPolicy {
        return PeriodDiscountPolicy(percent)
    }
}

abstract class MovieDiscountPolicy(
    val discountConditions: MutableSet<DiscountCondition>
) {
    lateinit var discountPolicyType: MovieDiscountPolicyType

    fun setDiscountConditions() {
        this.discountConditions.addAll(discountConditions)
    }

    protected abstract fun calculate(): Long
}

class SequenceDiscountPolicy(
    discountConditions: MutableSet<DiscountCondition>,
    private val sequence: Int
) : MovieDiscountPolicy(discountConditions) {
    override fun calculate(): Long {
        return 0L
    }
}

class PeriodDiscountPolicy(
    discountConditions: MutableSet<DiscountCondition>,
    private val startDate: ZonedDateTime,
    private val endDate: ZonedDateTime,
    private val dayOfWeek: Int
) : MovieDiscountPolicy(discountConditions) {
    override fun calculate(): Long {
        return 0L
    }
}

interface DiscountCondition {
}