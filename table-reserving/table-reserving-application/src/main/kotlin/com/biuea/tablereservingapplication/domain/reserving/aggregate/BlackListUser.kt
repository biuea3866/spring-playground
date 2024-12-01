package com.biuea.tablereservingapplication.domain.reserving.aggregate

import com.biuea.tablereservingapplication.core.Id

class BlackListUser private constructor(
    _id: Id,
    _userId: Id,
    _nickname: String,
    _reason: String
) {
    var id: Id = _id
        private set
    var userId: Id = _userId
        private set
    var nickname: String = _nickname
        private set
    var reason: String = _reason
        private set

    companion object {
        fun create(
            id: Id,
            userId: Id,
            nickname: String,
            reason: String
        ): BlackListUser {
            return BlackListUser(
                _id = id,
                _userId = userId,
                _nickname = nickname,
                _reason = reason
            )
        }
    }
}