package com.biuea.tablereservingapplication.domain.restaurant.vo

import java.time.ZonedDateTime

data class MenuImage(
    private var _bucket: String,
    private var _key: String,
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
        ): MenuImage {
            return MenuImage(
                _bucket = bucket,
                _key = key,
                _createdAt = ZonedDateTime.now(),
                _updatedAt = ZonedDateTime.now(),
                _deletedAt = null
            )
        }
    }
}