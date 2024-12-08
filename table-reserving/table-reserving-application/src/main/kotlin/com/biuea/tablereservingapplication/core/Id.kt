package com.biuea.tablereservingapplication.core

@JvmInline
value class Id(
    private val _id: Long
){
    val id get() = _id
    init {
        require(_id > 0) { "Id must be greater than 0" }
    }
}
