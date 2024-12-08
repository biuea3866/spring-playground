package com.biuea.tablereservingapplication.config

import com.biuea.tablereservingapplication.application.authorization.ValidateGrantedGradeFacade
import com.biuea.tablereservingapplication.core.HttpException
import com.biuea.tablereservingapplication.utils.AuthorizeGrade
import jakarta.servlet.*
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler
import java.lang.Exception

@Component
class AuthorizeGradeInterceptor(
    private val validateGrantedGradeFacade: ValidateGrantedGradeFacade
): HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        if (handler is ResourceHttpRequestHandler) {
            return true
        }

        val availableGrade = (handler as HandlerMethod).getMethodAnnotation(AuthorizeGrade::class.java)
            ?: throw HttpException.ForbiddenException("인가되지 않은 기능입니다.")
        val userId = request.getAttribute("userId") as Long

        this.validateGrantedGradeFacade.execute(userId, availableGrade.grade)

        return true
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