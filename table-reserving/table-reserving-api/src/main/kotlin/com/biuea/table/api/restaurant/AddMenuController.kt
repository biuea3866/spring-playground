package com.biuea.table.api.restaurant

import com.biuea.tablereservingapplication.application.restaurant.AddMenuFacade
import com.biuea.tablereservingapplication.core.ApiResponse
import com.biuea.tablereservingapplication.domain.user.aggregate.UserGrade
import com.biuea.tablereservingapplication.utils.AuthorizeGrade
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class AddMenuController(
    private val addMenuFacade: AddMenuFacade
) {
    @AuthorizeGrade(UserGrade.BRONZE)
    @PostMapping("/restaurant/menu")
    fun addMenu(
        @RequestHeader("X-User-Id") userId: Long,
        @Valid @RequestBody request: AddMenuRequest
    ): ApiResponse<Boolean> {
        addMenuFacade.execute()

        return ApiResponse.success(httpStatus = HttpStatus.CREATED)
    }
}

data class AddMenuRequest(
    val restaurantId: Long,
    val name: String,
    val price: Int,
    val description: String
)