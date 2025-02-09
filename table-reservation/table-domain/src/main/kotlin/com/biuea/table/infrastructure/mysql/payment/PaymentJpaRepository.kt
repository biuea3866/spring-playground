package com.biuea.table.infrastructure.mysql.payment

import com.biuea.table.domain.payment.Payment
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentJpaRepository : JpaRepository<Payment, Long> {
}