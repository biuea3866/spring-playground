package com.biuea.tablereservingapplication.domain.reserving.event

class CancelledEventPayload(
    val userId: String,
    val restaurantId: String,
    val restaurantName: String,
    val menuNames: List<String>
) {
}