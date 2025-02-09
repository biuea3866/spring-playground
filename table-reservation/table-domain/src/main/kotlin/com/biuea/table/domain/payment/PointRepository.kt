package com.biuea.table.domain.payment

interface PointRepository {
    fun findBy(userId: Long): Point?
    fun update(point: Point): Boolean
}