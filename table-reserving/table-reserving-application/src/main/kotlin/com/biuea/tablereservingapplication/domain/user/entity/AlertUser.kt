package com.biuea.tablereservingapplication.domain.user.entity

import com.biuea.tablereservingapplication.core.Id

enum class AlertType {
    RESERVE,
    PAYMENT
}

class AlertUser private constructor(
    _id: Id,
    _content: String,
    _type: AlertType
) {
    var id: Id = _id
        private set
    var content: String = _content
        private set
    var type: AlertType = _type
        private set

    companion object {
        fun create(
            id: Id,
            content: String,
            type: AlertType
        ): AlertUser {
            return AlertUser(
                _id = id,
                _content = content,
                _type = type
            )
        }
    }
}