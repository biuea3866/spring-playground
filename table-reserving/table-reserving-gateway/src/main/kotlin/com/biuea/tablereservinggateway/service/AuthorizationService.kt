package com.biuea.tablereservinggateway.service

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class AuthorizationService(
    private val webClient: WebClient
) {
    fun authorizePlatformUser(userId: Long): Mono<Boolean> {
        return webClient.get()
            .uri("http://localhost:8080/internal/authorization/platform-user?userId=$userId")
            .retrieve()
            .bodyToMono(Boolean::class.java)
    }
}