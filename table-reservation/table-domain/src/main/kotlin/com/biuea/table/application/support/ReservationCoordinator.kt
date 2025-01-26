package com.biuea.table.application.support

import com.biuea.table.domain.reservation.ReservationEntity
import com.biuea.table.domain.restaurant.ConfirmWaitingNumberStrategy
import com.biuea.table.domain.restaurant.RequestWaitingNumberStrategy
import com.biuea.table.domain.restaurant.RestaurantEntity
import com.biuea.table.domain.restaurant.WaitingNumberStrategyRouter

class ReservationCoordinator {
    private val waitingNumberStrategy: WaitingNumberStrategyRouter = WaitingNumberStrategyRouter()

    fun issueWaitingNumber(
        reservation: ReservationEntity,
        restaurant: RestaurantEntity,
        notEntranceCustomers: Int
    ): Int {
        return when(restaurant.isTurnedOnAutoConfirm()) {
            true -> {
                reservation.confirm(notEntranceCustomers)
                waitingNumberStrategy.findBy<RequestWaitingNumberStrategy>()
                    .issue(restaurant, notEntranceCustomers)
            }

            false -> waitingNumberStrategy.findBy<ConfirmWaitingNumberStrategy>()
                .issue(restaurant, notEntranceCustomers)
        }
    }
}