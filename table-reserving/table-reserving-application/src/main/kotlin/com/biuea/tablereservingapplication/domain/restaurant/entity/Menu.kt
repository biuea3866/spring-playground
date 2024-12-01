package com.biuea.tablereservingapplication.domain.restaurant.model

import com.biuea.tablereservingapplication.core.Id

class Menu private constructor(
    val id: Id,
    val restaurantId: Id,
) {
}