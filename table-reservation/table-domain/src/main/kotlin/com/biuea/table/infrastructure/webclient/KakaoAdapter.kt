package com.biuea.table.infrastructure.webclient

import com.biuea.table.domain.alert.AlertMessage
import com.biuea.table.domain.alert.AlertMessageSender
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class KakaoAdapter(
    private val webClient: WebClient
): AlertMessageSender {
    override fun send(message: AlertMessage) {
        webClient.post()
            .uri("https://kakao.com")
            .bodyValue(message)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
    }
}