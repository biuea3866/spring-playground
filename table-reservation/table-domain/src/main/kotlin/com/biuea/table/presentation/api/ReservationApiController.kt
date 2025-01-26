package com.biuea.table.presentation.api

import com.biuea.table.application.ReservationRequest
import com.biuea.table.application.ReservationRequestApplication
import com.biuea.table.common.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ReservationApiController(
    private val reservationRequestApplication: ReservationRequestApplication
) {
    @PostMapping("/reservation/request")
    fun requestReservation(
        @RequestBody request: ReservationRequestBody
    ): ApiResponse<Unit> {
        reservationRequestApplication.execute(request.toReservationRequest())

        return ApiResponse.success(Unit)
    }
}