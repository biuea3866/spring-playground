package com.biuea.tablereservingapplication.domain.owner.entity

import java.time.ZonedDateTime

enum class OwnerAlertType {
    REVIEW,
    RESERVING,
}

class OwnerAlert private constructor(
    _id: String,
    _ownerId: String,
    _type: OwnerAlertType,
    _createdAt: ZonedDateTime,
    _updatedAt: ZonedDateTime,
    _deletedAt: ZonedDateTime?
) {
    var id: String = _id
        private set
    var ownerId: String = _ownerId
        private set
    var type: OwnerAlertType = _type
        private set
    var createdAt: ZonedDateTime = _createdAt
        private set

    companion object {
        fun create(
            id: String,
            ownerId: String,
            type: OwnerAlertType,
            createdAt: ZonedDateTime,
            updatedAt: ZonedDateTime,
            deletedAt: ZonedDateTime?
        ): OwnerAlert {
            return OwnerAlert(
                _id = id,
                _ownerId = ownerId,
                _type = type,
                _createdAt = createdAt,
                _updatedAt = updatedAt,
                _deletedAt = deletedAt
            )
        }
    }
}