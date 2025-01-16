package com.biuea.objectpractice.movie

import java.time.DayOfWeek
import java.time.ZonedDateTime

//class MovieDiscountPolicyFactory(
//    val discountPolicy: MovieDiscountPolicy
//) {
//    fun calculate(screening: Screening): Long {
//        return 0L
//    }
//
//    companion object {
//        fun createPercentDiscountPolicy(
//            percent: Double,
//            discountConditions: MutableSet<DiscountCondition>
//        ): PercentDiscountPolicy {
//            return PercentDiscountPolicy(percent, discountConditions)
//        }
//
//        fun createAmountDiscountPolicy(
//            amount: Long,
//            discountConditions: MutableSet<DiscountCondition>
//        ): AmountDiscountPolicy {
//            return AmountDiscountPolicy(amount, discountConditions)
//        }
//    }
//}

abstract class MovieDiscountPolicy(
    val discountConditions: MutableSet<DiscountCondition>
) {
    fun addDiscountCondition(discountCondition: MutableSet<DiscountCondition>) {
        discountConditions.addAll(discountCondition)
    }

    fun copyCondition(policy: MovieDiscountPolicy) {
        policy.discountConditions.addAll(this.discountConditions)
    }

    // 영화의 할인 금액을 계산한다.
    fun calculate(
        screening: Screening,
        count: Int,
        fee: Money
    ): Money {
        // 등록된 할인 조건들을 순회하여 만족하는 조건이 있으면 할인 금액을 반환한다.
        for (discountCondition in discountConditions) {
            if (discountCondition.isSatisfiedBy(screening, count)) {
                return getDiscountAmount(screening)
            }
        }

        return Money.ZERO
    }

    // 각 할인 정책 구현체에서 할인 금액을 반환한다.
    abstract fun getDiscountAmount(screening: Screening): Money
}
//
//class PercentDiscountPolicy(
//    val percent: Double,
//    discountConditions: MutableSet<DiscountCondition>
//) : MovieDiscountPolicy(discountConditions) {
//    override fun getDiscountAmount(screening: Screening): Money {
//        return Money.wons(screening.retrieveFee().times(percent))
//    }
//}

//class AmountDiscountPolicy(
//    val amount: Long,
//    discountConditions: MutableSet<DiscountCondition>
//) : MovieDiscountPolicy(discountConditions) {
//    override fun getDiscountAmount(screening: Screening): Money {
//        return Money.wons(screening.retrieveFee().minus(amount))
//    }
//}

interface DiscountCondition {
    // 조건
    fun isSatisfiedBy(
        screening: Screening,
        count: Int
    ): Boolean

    // 액션
    fun calculateFee(fee: Money): Money
}

//class SequenceDisCountCondition(
//    val sequence: Int
//) : DiscountCondition {
//    override fun isSatisfiedBy(screening: Screening): Boolean {
//        return screening.isSequence(sequence)
//    }
//}
//
//class PeriodDiscountCondition(
//    val dayOfWeek: DayOfWeek,
//    val startTime: ZonedDateTime,
//    val endTime: ZonedDateTime
//) : DiscountCondition {
//    override fun isSatisfiedBy(screening: Screening): Boolean {
//        return screening.screeningDates.any {
//            it.date.dayOfWeek == dayOfWeek &&
//            it.timeSlots.any { timeSlot ->
//                timeSlot.startTime >= startTime &&
//                timeSlot.endTime <= endTime
//            }
//        }
//    }
//}