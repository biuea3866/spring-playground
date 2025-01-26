package com.biuea.table.domain.restaurant

interface RestaurantRepository {
    fun findBy(id: Long): RestaurantEntity?
}