package com.biuea.objectpractice.programmer

// Paper를 기반으로 프로그램을 만들 책임이 있다.
//interface Programmer {
//    fun makeProgram(paper: Paper): Program
//}

// 중복이라 판단되는 로직이 존재한다면 추상클래스로 전환하여 hook method를 사용한다.
// paper에게 책임을 위임하였더니 다운캐스팅이 발생하고 OCP를 위반한다.
// 따라서 Programmer에게 책임을 위임한다.
abstract class Programmer<T: Paper> {
    fun getProgram(paper: T): Program {
        setData(paper)
        return makeProgram(paper)
    }

    abstract fun setData(paper: T)
    abstract fun makeProgram(paper: T): Program
}

//class FrontEnd private constructor(
//    private var language: Language,
//    private var library: Library
//) : Programmer() {
//    constructor() : this("kotlinJS", "vueJS")
//
//    override fun makeProgram(paper: Paper): Program {
//        // paper에서 어떤 정보도 얻을 수 없으므로 다운캐스팅을 통해 정보를 얻는다.
//        // 리스코프 치환 원칙에 어긋난다.
//        // 다운캐스팅은 OCP를 위반한다.
////        if (paper is Client) {
////            this.library = paper.library
////            this.language = paper.language
////        }
//        // 다운캐스팅하지않고, paper에게 책임을 위임한다.
//        paper.setData(this)
//
//        return makeFrontEndProgram()
//    }
//
//    private fun makeFrontEndProgram(): Program {
//        return Program()
//    }
//
//    fun setLanguage(language: Language) {
//        this.language = language
//    }
//
//    fun setLibrary(library: Library) {
//        this.library = library
//    }
//}

//class BackEnd private constructor(
//    private var language: Language,
//    private var server: Server
//) : Programmer() {
//    constructor() : this("kotlin", "Spring")
//
//    override fun makeProgram(paper: Paper): Program {
//        // if를 사용하게 되면 런타임에 분기를 맞기는 것이다.
//        // if를 사용하는 클라이언트 쪽으로 분기를 옮기는 것이 좋다.
//        paper.setData(this)
//
//        return makeBackEndProgram()
//    }
//
//    private fun makeBackEndProgram(): Program {
//        return Program()
//    }
//
//    fun setServer(server: Server) {
//        this.server = server
//    }
//
//    fun setLanguage(language: Language) {
//        this.language = language
//    }
//}

// lsp, ocp를 지키기 위해서 자신의 조건문을 더 상위레벨로 보내야한다.
abstract class BackEnd<T: Paper>(
    protected var language: Language = Language("kotlin"),
    protected var server: Server = Server("Spring")
) : Programmer<T>() {
    override fun makeProgram(paper: T): Program {
        return Program()
    }
}

abstract class FrontEnd<T: Paper>(
    protected var language: Language = Language("kotlinJS"),
    protected var library: Library = Library("vueJS")
) : Programmer<T>() {
    override fun makeProgram(paper: T): Program {
        return Program()
    }
}