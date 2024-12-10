package com.biuea.tablereservinggateway.filter

import com.biuea.tablereservinggateway.service.AuthenticationService
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class AppAuthenticationFilter(
    private val authenticationService: AuthenticationService
): AbstractGatewayFilterFactory<AppAuthenticationFilter.Config>() {
    class Config

    override fun apply(config: Config?): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val token = exchange.request.headers["Authorization"]?.firstOrNull()

            when (token == null) {
                true -> {
                    exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                    exchange.response.setComplete()
                }
                false -> {
                    runCatching {
                        authenticationService.authenticateAppToken(token)
                            .flatMap {
                                exchange.attributes["userId"] = it
                                chain.filter(exchange)
                            }
                    }.getOrElse {
                        exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                        exchange.response.setComplete()
                    }
                }
            }
        }
    }
}