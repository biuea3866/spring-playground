package com.biuea.table.domain.restaurant

class WaitingNumberStrategyRouter {
    private val strategies: MutableSet<WaitingNumberStrategy> = mutableSetOf()

    fun add(strategies: Set<WaitingNumberStrategy>) {
        this.strategies.addAll(strategies)
    }

    fun getStrategies(): Set<WaitingNumberStrategy> {
        return this.strategies
    }

    inline fun <reified T : WaitingNumberStrategy> findBy(): T {
        return this.getStrategies().filterIsInstance<T>().firstOrNull()
            ?: throw IllegalArgumentException("Not found strategy: ${T::class.simpleName}")
    }
}

interface WaitingNumberStrategy {
    fun issue(restaurant: RestaurantEntity, notEntranceCustomers: Int): Int
}

class ConfirmWaitingNumberStrategy : WaitingNumberStrategy {
    override fun issue(restaurant: RestaurantEntity, notEntranceCustomers: Int): Int {
        return if (restaurant.isRemainTable()) {
            restaurant.occupyTable()
            0
        } else {
            notEntranceCustomers + 1
        }
    }
}

class RequestWaitingNumberStrategy: WaitingNumberStrategy {
    override fun issue(restaurant: RestaurantEntity, notEntranceCustomers: Int): Int {
        return -1
    }
}