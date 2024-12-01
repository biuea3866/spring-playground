package com.biuea.tablereservingapplication.domain.user.aggregate

import com.biuea.tablereservingapplication.core.Id

class FavoriteAggregate private constructor(
    private val _id: Id,
    private val _userId: Id,
    private val _restaurantId: Id
) {
    val id: Id get() = _id
    val userId: Id get() = _userId
    val restaurantId: Id get() = _restaurantId

    companion object {
        fun create(
            id: Id,
            userId: Id,
            restaurantId: Id
        ): FavoriteAggregate {
            return FavoriteAggregate(
                _id = id,
                _userId = userId,
                _restaurantId = restaurantId
            )
        }
    }
}