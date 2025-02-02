package com.biuea.table.gateway.service

import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

@Service
class AuthenticationService(
    private val webClient: WebClient
) {
    fun authenticateAppToken(token: String): Mono<Long> {
        return webClient.get()
            .uri {
                UriComponentsBuilder.fromUriString("http://localhost:8080/authentication/token/")
                    .build()
                    .encode()
                    .toUri()
            }
            .headers { h -> h.setBearerAuth(token) }
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Long::class.java)
    }
}