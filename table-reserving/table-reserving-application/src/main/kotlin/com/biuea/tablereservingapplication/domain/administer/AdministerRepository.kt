package com.biuea.tablereservingapplication.domain.administer

import com.biuea.tablereservingapplication.core.Id

interface AdministerRepository {
    fun findById(administerId: Long): AdministerAggregation?
}