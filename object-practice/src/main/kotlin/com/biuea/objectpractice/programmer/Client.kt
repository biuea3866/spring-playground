package com.biuea.objectpractice.programmer

class Client private constructor(
    val library: Library,
    val language: Language,
    private var _programmer: Programmer
): Paper {

}