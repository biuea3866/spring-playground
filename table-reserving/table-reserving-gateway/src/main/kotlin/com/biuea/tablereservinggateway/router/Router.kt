package com.biuea.tablereservinggateway.router

import com.biuea.tablereservinggateway.filter.AppAuthenticationFilter
import com.biuea.tablereservinggateway.filter.LoggingFilter
import com.biuea.tablereservinggateway.filter.PlatformUserAuthorizationFilter
import com.biuea.tablereservinggateway.filter.TraceFilter
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.RouteLocatorDsl
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Router(
    private val appAuthenticationFilter: AppAuthenticationFilter,
    private val platformUserAuthorizationFilter: PlatformUserAuthorizationFilter,
    private val loggingFilter: LoggingFilter,
    private val traceFilter: TraceFilter
) {
    @Bean
    fun routingPattern(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes {
            appRouting(this)
        }
    }

    private fun appRouting(builder: RouteLocatorDsl) {
        builder.route(id = "app-api-routing") {
            path("/app")
                .filters { f ->
                    f.filter(loggingFilter.apply(LoggingFilter.Config()))
                    f.filter(traceFilter.apply(TraceFilter.Config()))
                    f.filter(appAuthenticationFilter.apply(AppAuthenticationFilter.Config()))
                    f.filter(platformUserAuthorizationFilter.apply(PlatformUserAuthorizationFilter.Config()))
                    f.rewritePath("/app", "/api/app")
                }
                .uri("http://localhost:8081")
        }
    }
}