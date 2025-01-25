package com.biuea.reservation.application.port.out

interface RestaurantOutputPort {
    fun calculateReservationCount(restaurantId: Long): Int
}