package com.biuea.table.application.support

import com.biuea.table.domain.reservation.ReservationEntity
import com.biuea.table.domain.restaurant.ConfirmWaitingNumberStrategy
import com.biuea.table.domain.restaurant.RequestWaitingNumberStrategy
import com.biuea.table.domain.restaurant.RestaurantEntity

class ReservationCoordinator {
    fun issueWaitingNumber(
        reservation: ReservationEntity,
        restaurant: RestaurantEntity,
        notEntranceCustomers: Int
    ) {
        when(restaurant.isTurnedOnAutoConfirm()) {
            true -> {
                restaurant.changeWaitingNumberStrategy(ConfirmWaitingNumberStrategy(restaurant.isRemainTable(), notEntranceCustomers))
                val waitingNumber = restaurant.issueWaitingNumber()
                reservation.confirm(waitingNumber)
            }

            false -> {
                restaurant.changeWaitingNumberStrategy(RequestWaitingNumberStrategy())
                restaurant.issueWaitingNumber()
            }
        }
    }
}