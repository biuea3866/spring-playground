package com.biuea.table.infrastructure.mysql.restaurant

import com.biuea.table.domain.restaurant.RestaurantEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RestaurantJpaRepository: JpaRepository<RestaurantEntity, Long> {
}