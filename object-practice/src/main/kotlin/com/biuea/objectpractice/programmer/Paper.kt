package com.biuea.objectpractice.programmer

interface Paper {
}

class Client private constructor(
    val library: Library,
    val language: Language,
    private var _programmer: Programmer
): Paper {
    fun setProgrammer(programmer: Programmer) {
        _programmer = programmer
    }
}

class ServerClient(
    private val server: Server,
    private val backendLanguage: Language,
    private val frontendLanguage: Language,
    private var _backendProgrammer: Programmer,
    private var _frontendProgrammer: Programmer
): Paper {
    val backendProgrammer: Programmer
        get() = _backendProgrammer

    val frontendProgrammer: Programmer
        get() = _frontendProgrammer

    fun setFrontEndProgrammer(programmer: Programmer) {
        _frontendProgrammer = programmer
    }

    fun setBackEndProgrammer(programmer: Programmer) {
        _backendProgrammer = programmer
    }
}