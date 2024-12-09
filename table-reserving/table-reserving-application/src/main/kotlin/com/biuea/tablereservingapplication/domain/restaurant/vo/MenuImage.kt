package com.biuea.tablereservingapplication.domain.restaurant.vo

import com.biuea.tablereservingapplication.core.JpaEntity
import java.time.ZonedDateTime

data class MenuImage(
    private var _bucket: String,
    private var _key: String,
    private var _filename: String,
    private val _createdAt: ZonedDateTime,
    private var _updatedAt: ZonedDateTime,
    private var _deletedAt: ZonedDateTime?
) {
    val bucket get() = this._bucket
    val key get() = this._key

    fun updateImage(bucket: String, key: String): MenuImage {
        this._bucket = bucket
        this._key = key
        this._updatedAt = ZonedDateTime.now()

        return this
    }

    fun deleteImage(): MenuImage {
        this._deletedAt = ZonedDateTime.now()

        return this
    }

    companion object {
        fun create(
            bucket: String,
            key: String,
            filename: String
        ): MenuImage {
            return MenuImage(
                _bucket = bucket,
                _key = key,
                _filename = filename,
                _createdAt = ZonedDateTime.now(),
                _updatedAt = ZonedDateTime.now(),
                _deletedAt = null
            )
        }

        fun JpaEntity.of(
            bucket: String,
            key: String,
            filename: String,
            createdAt: ZonedDateTime,
            updatedAt: ZonedDateTime,
            deletedAt: ZonedDateTime?
        ): MenuImage {
            return MenuImage(
                _bucket = bucket,
                _key = key,
                _filename = filename,
                _createdAt = createdAt,
                _updatedAt = updatedAt,
                _deletedAt = deletedAt
            )
        }
    }
}