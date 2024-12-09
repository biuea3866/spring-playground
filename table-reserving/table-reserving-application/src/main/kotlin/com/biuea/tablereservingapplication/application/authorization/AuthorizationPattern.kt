package com.biuea.tablereservingapplication.application.authorization

import com.biuea.tablereservingapplication.core.HttpException
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

abstract class AuthorizationPattern {
    abstract fun<T> execute(argument: T)
}

inline fun<reified T: AuthorizationPattern> createContext(): AuthorizationPattern {
    return T::class.createInstance()
}
