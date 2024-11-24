package com.biuea.tablereservingapplication.core

@JvmInline
value class Id(
    private val id: Long
){
    init {
        require(id > 0) { "Id must be greater than 0" }
    }
}
