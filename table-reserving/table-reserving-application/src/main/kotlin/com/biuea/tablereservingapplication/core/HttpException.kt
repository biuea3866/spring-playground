package com.biuea.tablereservingapplication.core

import org.springframework.http.HttpStatus

sealed class HttpException(
    val status: HttpStatus,
    override val message: String? = null,
): Exception() {
    class BadRequestException(message: String?): HttpException(
        status = HttpStatus.BAD_REQUEST,
        message = message
    )
    class UnauthorizedException(message: String?): HttpException(
        status = HttpStatus.UNAUTHORIZED,
        message = message
    )
    class ForbiddenException(message: String?): HttpException(
        status = HttpStatus.FORBIDDEN,
        message = message
    )
    class NotFoundException(message: String?): HttpException(
        status = HttpStatus.NOT_FOUND,
        message = message
    )
    class ConflictException(message: String?): HttpException(
        status = HttpStatus.CONFLICT,
        message = message
    )
    class InternalServerErrorException(message: String?): HttpException(
        status = HttpStatus.INTERNAL_SERVER_ERROR,
        message = message
    )
    class ServiceUnavailableException(message: String?): HttpException(
        status = HttpStatus.SERVICE_UNAVAILABLE,
        message = message
    )
    class GatewayTimeoutException(message: String?): HttpException(
        status = HttpStatus.GATEWAY_TIMEOUT,
        message = message
    )
}