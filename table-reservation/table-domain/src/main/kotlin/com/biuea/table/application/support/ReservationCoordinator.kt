package com.biuea.table.application.support

import com.biuea.table.domain.reservation.ReservationEntity
import com.biuea.table.domain.restaurant.ConfirmWaitingNumberStrategy
import com.biuea.table.domain.restaurant.RequestWaitingNumberStrategy
import com.biuea.table.domain.restaurant.RestaurantEntity

class ReservationCoordinator {
    fun issueWaitingNumber(
        reservation: ReservationEntity,
        restaurant: RestaurantEntity
    ) {
        when(restaurant.isTurnedOnAutoConfirm()) {
            true -> {
                val waitingNumber = restaurant.issueWaitingNumber()
                restaurant.changeWaitingNumberStrategy(ConfirmWaitingNumberStrategy(restaurant.isRemainTable(), waitingNumber))
                reservation.confirm(waitingNumber)
            }

            false -> {
                restaurant.changeWaitingNumberStrategy(RequestWaitingNumberStrategy())
                restaurant.issueWaitingNumber()
            }
        }
    }
}