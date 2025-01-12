package com.biuea.objectpractice.programmer

// Paper를 기반으로 프로그램을 만들 책임이 있다.
interface Programmer {
    fun makeProgram(paper: Paper): Program
}

class FrontEnd private constructor(
    private var language: Language,
    private var library: Library
) : Programmer {
    constructor() : this("kotlinJS", "vueJS")

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

class BackEnd private constructor(
    private var language: Language,
    private var library: Library
) : Programmer {
    constructor() : this("kotlin", "Spring")

    override fun makeProgram(paper: Paper): Program {
        // if를 사용하게 되면 런타임에 분기를 맞기는 것이다.
        // if를 사용하는 클라이언트 쪽으로 분기를 옮기는 것이 좋다.
        if (paper is Client) {
            this.library = paper.library
            this.language = paper.language
        }

        return makeBackEndProgram()
    }

    private fun makeBackEndProgram(): Program {
        return Program()
    }
}