package com.biuea.objectpractice.programmer

// Paper를 기반으로 프로그램을 만들 책임이 있다.
interface Programmer {
    fun makeProgram(paper: Paper): Program

}

class FrontEnd private constructor(
    private var language: Language,
    private var library: Library
) : Programmer {
    override fun makeProgram(paper: Paper): Program {
        // paper에서 어떤 정보도 얻을 수 없으므로 다운캐스팅을 통해 정보를 얻는다.
        // 리스코프 치환 원칙에 어긋난다.
        if (paper is Client) {
            this.library = paper.library
            this.language = paper.language
        }

        return makeFrontEndProgram()
    }

    private fun makeFrontEndProgram(): Program {
        return Program()
    }
}