package com.biuea.tablereservingapplication.domain.owner.repository

import com.biuea.tablereservingapplication.core.Id
import com.biuea.tablereservingapplication.domain.owner.aggregate.OwnerAggregation

interface OwnerRepository {
    fun findById(ownerId: Id): OwnerAggregation?
}