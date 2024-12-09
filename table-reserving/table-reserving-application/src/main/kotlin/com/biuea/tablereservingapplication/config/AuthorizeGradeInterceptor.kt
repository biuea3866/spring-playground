package com.biuea.tablereservingapplication.config

import com.biuea.tablereservingapplication.application.authorization.*
import com.biuea.tablereservingapplication.core.HttpException
import com.biuea.tablereservingapplication.utils.AuthorizeGrade
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler
import java.lang.Exception

@Component
class AuthorizeGradeInterceptor: HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        if (handler is ResourceHttpRequestHandler) {
            return true
        }

        router(request, handler)

        return true
    }

    private fun router(
        request: HttpServletRequest,
        handler: Any
    ) {
        when {
            request.contextPath.contains("/app") -> {
                val userId = request.getAttribute("userId") as Long
                val availableGrade = (handler as HandlerMethod).getMethodAnnotation(AuthorizeGrade::class.java)
                    ?: throw HttpException.ForbiddenException("인가되지 않은 기능입니다.")
                createContext<AuthorizePlatformUserFacade>()
                    .execute(AuthorizePlatformUserInput(userId, availableGrade.grade))
            }
            request.contextPath.contains("/admin") -> {
                val userId = request.getAttribute("administerId") as Long
                val availableGrade = (handler as HandlerMethod).getMethodAnnotation(AuthorizeGrade::class.java)
                    ?: throw HttpException.ForbiddenException("인가되지 않은 기능입니다.")
                authorizationFactory.getPattern { AuthorizePlatformUserInput(userId, availableGrade.grade) }
            }
            request.contextPath.contains("/owner") ->
        }
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        super.postHandle(request, response, handler, modelAndView)
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        super.afterCompletion(request, response, handler, ex)
    }
}