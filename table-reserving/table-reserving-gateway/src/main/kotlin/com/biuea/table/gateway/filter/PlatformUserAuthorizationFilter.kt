package com.biuea.table.gateway.filter

import com.biuea.table.gateway.service.AuthorizationService
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class PlatformUserAuthorizationFilter(
    private val authorizationService: AuthorizationService
): AbstractGatewayFilterFactory<PlatformUserAuthorizationFilter.Config>() {
    class Config

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val userId = exchange.attributes["userId"] as Long

            runCatching {
                this.authorizationService.authorizePlatformUser(userId)
                    .flatMap {
                        if (!it) {
                            return@flatMap Mono.error(RuntimeException("Unauthorized"))
                        }
                        exchange.request.headers["X-User-Id"] = userId.toString()
                        chain.filter(exchange)
                    }
            }.getOrElse {
                exchange.response.statusCode = HttpStatus.FORBIDDEN
                exchange.response.setComplete()
            }
        }
    }
}