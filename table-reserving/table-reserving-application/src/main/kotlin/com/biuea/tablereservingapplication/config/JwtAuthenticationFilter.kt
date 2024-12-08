package com.biuea.tablereservingapplication.config

import com.biuea.tablereservingapplication.core.HttpException
import com.biuea.tablereservingapplication.utils.AuthenticationUtils
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

/**
 * Filter: 웹 애플리케이션의 Context 기능
 * GenericFilterBean: Filter를 확장하여 Spring의 설정 정보를 얻어올 수 있다.
 * Interceptor: Spring의 Context 기능, Spring Container에 등록된 Bean을 사용할 수 있다.
 * OncePerRequestFilter: 사용자 요청에 대해 한번만 수행되는 필터이다.
 */
@Component
class JwtAuthenticationFilter(
    private val authenticationUtils: AuthenticationUtils
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader("Authorization")

        if (token == null || token.startsWith("Bearer").not()) {
            throw HttpException.UnauthorizedException(message = "Invalid token")
        }

        val jwt = token.substring(7)

        if (this.authenticationUtils.isTokenValid(jwt).not()) {
            throw HttpException.UnauthorizedException(message = "Invalid token")
        }

        val claims = this.authenticationUtils.extractClaims(jwt)
        claims.keys.forEach { request.setAttribute(it, claims[it]) }

        filterChain.doFilter(request, response)
    }
}