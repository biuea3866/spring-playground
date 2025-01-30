package com.biuea.table.gateway.filter

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class TraceFilter: AbstractGatewayFilterFactory<TraceFilter.Config>(Config::class.java) {
    class Config

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val uuid = UUID.randomUUID().toString()

            exchange.attributes["traceId"] = uuid

            chain.filter(exchange)
        }
    }
}