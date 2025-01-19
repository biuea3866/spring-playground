package com.biuea.tablereservingapplication.application.authorization

import kotlin.reflect.full.createInstance

abstract class AuthorizationPattern {
    abstract fun<T> execute(argument: T)
}

inline fun<reified T: AuthorizationPattern> createContext(): AuthorizationPattern {
    return T::class.createInstance()
}
