package com.biuea.table.common

import org.springframework.http.HttpStatus

sealed class HttpException(
    override val message: String,
    val statusCode: HttpStatus
): Exception() {
    class UnauthorizedException(message: String): HttpException(message, HttpStatus.UNAUTHORIZED)
    class ForbiddenException(message: String): HttpException(message, HttpStatus.FORBIDDEN)
    class BadRequestException(message: String): HttpException(message, HttpStatus.BAD_REQUEST)
    class NotFoundException(message: String): HttpException(message, HttpStatus.NOT_FOUND)
    class ConflictException(message: String): HttpException(message, HttpStatus.CONFLICT)
    class InternalServerErrorException(message: String): HttpException(message, HttpStatus.INTERNAL_SERVER_ERROR)
    class ServiceUnavailableException(message: String): HttpException(message, HttpStatus.SERVICE_UNAVAILABLE)
    class GatewayTimeoutException(message: String): HttpException(message, HttpStatus.GATEWAY_TIMEOUT)
    class TooManyRequestsException(message: String): HttpException(message, HttpStatus.TOO_MANY_REQUESTS)
    class UnprocessableEntityException(message: String): HttpException(message, HttpStatus.UNPROCESSABLE_ENTITY)
    class UnsupportedMediaTypeException(override val message: String): HttpException(message, HttpStatus.UNSUPPORTED_MEDIA_TYPE)
}