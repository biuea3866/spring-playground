package com.biuea.tablereservingapplication.config

import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Component
class WebMvcConfigurer(
    private val authorizeGradeInterceptor: AuthorizeGradeInterceptor
): WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.authorizeGradeInterceptor()
    }

    private fun InterceptorRegistry.authorizeGradeInterceptor() {
        this.addInterceptor(authorizeGradeInterceptor)
            .excludePathPatterns("/app/authentication/**")
    }
}