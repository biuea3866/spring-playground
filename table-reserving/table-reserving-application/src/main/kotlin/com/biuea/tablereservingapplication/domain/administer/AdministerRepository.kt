package com.biuea.tablereservingapplication.domain.administer

interface AdministerRepository {
    fun findById(administerId: Long): AdministerAggregation?
}