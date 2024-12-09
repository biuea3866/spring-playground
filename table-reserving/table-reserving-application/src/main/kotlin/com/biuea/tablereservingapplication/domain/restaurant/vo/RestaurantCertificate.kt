package com.biuea.tablereservingapplication.domain.restaurant.vo

import java.time.ZonedDateTime

data class RestaurantCertificate(
    private val _bucket: String,
    private val _key: String,
    private val _createdAt: ZonedDateTime,
    private val _updatedAt: ZonedDateTime,
    private val _deletedAt: ZonedDateTime?
) {
    val bucket get() = this._bucket
    val key get() = this._key

    fun change(
        bucket: String,
        key: String
    ): RestaurantCertificate {
        return this.copy(
            _bucket = bucket,
            _key = key,
            _updatedAt = ZonedDateTime.now(),
        )
    }

    companion object {
        fun create(
            bucket: String,
            key: String
        ): RestaurantCertificate {
            return RestaurantCertificate(
                _bucket = bucket,
                _key = key,
                _createdAt = ZonedDateTime.now(),
                _updatedAt = ZonedDateTime.now(),
                _deletedAt = null
            )
        }

        fun of(
            bucket: String,
            key: String,
            createdAt: ZonedDateTime,
            updatedAt: ZonedDateTime,
            deletedAt: ZonedDateTime?
        ): RestaurantCertificate {
            return RestaurantCertificate(
                _bucket = bucket,
                _key = key,
                _createdAt = createdAt,
                _updatedAt = updatedAt,
                _deletedAt = deletedAt
            )
        }
    }
}