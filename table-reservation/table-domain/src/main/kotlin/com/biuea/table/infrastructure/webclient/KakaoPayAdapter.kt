package com.biuea.table.infrastructure.webclient

import com.biuea.table.domain.payment.PaymentGateway
import com.biuea.table.domain.payment.PaymentGatewayResponse
import com.biuea.table.domain.payment.PaymentStatus
import com.biuea.table.domain.payment.PaymentType
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class KakaoPayAdapter(
    private val webClient: WebClient
): PaymentGateway() {
    override val paymentType = PaymentType.KAKAO_PAY

    override fun buildRefund(transactionId: String): PaymentGatewayResponse {
        return PaymentGatewayResponse(
            success = false,
            transactionId = transactionId,
            status = PaymentStatus.FAILED
        )
    }

    override fun buildPay(transactionId: String, paymentAmount: Int): PaymentGatewayResponse {
        val response = runCatching {
            webClient.post()
                .uri("/kakao-pay")
                .bodyValue(mapOf(
                    "transactionId" to transactionId,
                    "paymentAmount" to paymentAmount
                ))
                .retrieve()
                .bodyToMono(String::class.java)
                .block()
        }.getOrElse {
            when(it) {
                is IllegalArgumentException -> {
                    return PaymentGatewayResponse(
                        success = false,
                        transactionId = transactionId,
                        status = PaymentStatus.FAILED,
                        reason = it.message
                    )
                }
                else -> {
                    return PaymentGatewayResponse(
                        success = false,
                        transactionId = transactionId,
                        status = PaymentStatus.FAILED
                    )
                }
            }
        }

        return PaymentGatewayResponse(
            success = true,
            transactionId = transactionId,
            status = PaymentStatus.SUCCESS
        )
    }
}