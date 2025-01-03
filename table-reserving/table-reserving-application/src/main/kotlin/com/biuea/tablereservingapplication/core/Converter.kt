package com.biuea.tablereservingapplication.core

import java.time.ZonedDateTime

fun interface Converter<T, R> {
    fun convert(source: T): R
}

inline fun <reified T, R> Converter<T, R>.convert(source: T): R {
    return this.convert(source)
}

object StringToZonedDateTimeConverter: Converter<String, ZonedDateTime> {
    override fun convert(source: String): ZonedDateTime {
        return source
    }
}