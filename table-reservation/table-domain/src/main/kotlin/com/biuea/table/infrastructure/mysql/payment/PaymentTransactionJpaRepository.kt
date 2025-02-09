package com.biuea.table.infrastructure.mysql.payment

import com.biuea.table.domain.payment.PaymentTransaction
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentTransactionJpaRepository: JpaRepository<PaymentTransaction, Long> {
}