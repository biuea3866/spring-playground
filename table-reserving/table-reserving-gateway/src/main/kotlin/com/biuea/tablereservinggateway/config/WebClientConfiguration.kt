package com.biuea.tablereservinggateway.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.time.Duration
import java.util.concurrent.TimeUnit

@Configuration
class WebClientConfiguration {
    @Bean
    fun webClient(): WebClient {
        val provider = ConnectionProvider.builder("client")
            .maxConnections(100)
            .maxIdleTime(Duration.ofSeconds(4L))
            .maxLifeTime(Duration.ofSeconds(4L))
            .pendingAcquireTimeout(Duration.ofSeconds(5L))
            .pendingAcquireMaxCount(-1)
            .evictInBackground(Duration.ofSeconds(30L))
            .lifo()
            .metrics(true)
            .build()

        val httpClient = HttpClient.create(provider)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofMillis(5000))
            .doOnConnected { conn ->
                conn.addHandlerLast(
                    ReadTimeoutHandler(
                        5000,
                        TimeUnit.MILLISECONDS
                    )
                )
                    .addHandlerLast(
                        WriteTimeoutHandler(
                            5000,
                            TimeUnit.MILLISECONDS
                        )
                    )
            }
        val webClient = WebClient.builder()
            .defaultHeaders(
                { it.set("Content-Type", "application/json") }
            )
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .build()

        httpClient.warmup().block()

        return webClient
    }
}