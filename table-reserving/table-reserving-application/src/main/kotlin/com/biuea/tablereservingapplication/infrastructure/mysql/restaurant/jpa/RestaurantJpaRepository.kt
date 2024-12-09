package com.biuea.tablereservingapplication.infrastructure.mysql.restaurant.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface RestaurantJpaRepository: JpaRepository<RestaurantJpaEntity, Long> {
}