package com.biuea.objectpractice.movie

data class Money(
    val amount: Long
) {
    fun plus(amount: Money): Money {
        return this.copy(this.amount.plus(amount.amount))
    }

    fun minus(amount: Money): Money {
        return this.copy(this.amount.minus(amount.amount))
    }

    fun times(percent: Double): Money {
        return wons((this.amount * percent).toLong())
    }

    fun times(count: Int): Money {
        return wons((this.amount * count))
    }

    fun isLessThan(other: Money): Boolean {
        return this.amount < other.amount
    }

    fun isGreaterThanOrEqual(other: Money): Boolean {
        return this.amount >= other.amount
    }

    companion object {
        val ZERO = Money.wons(0)

        fun wons(amount: Long): Money {
            return Money(amount)
        }

        fun wons(amount: Double): Money {
            return Money(amount.toLong())
        }
    }
}