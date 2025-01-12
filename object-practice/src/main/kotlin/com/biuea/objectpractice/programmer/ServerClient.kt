package com.biuea.objectpractice.programmer

class ServerClient(
    private val server: Server,
    private val backendLanguage: Language,
    private val frontendLanguage: Language,
    private val backendProgrammer: Programmer,
    private val frontendProgrammer: Programmer
): Paper {
}