package com.biuea.objectpractice.programmer

//interface Paper {
//    fun setData(programmer: Programmer)
//}

// generic을 사용하여 다운캐스팅을 방지한다.
// generic은 추상형을 유지하면서 구상형을 제공한다.
interface Paper<T: Programmer> {
    fun setData(programmer: T)
}

// generic으로 변경하면서 구상형으로 제약되기 때문에 다운캐스팅을 방지하고 ocp, lsp를 지킬 수 있다.
class Client private constructor(
    val library: Library,
    val language: Language,
    private var _programmer: FrontEnd
): Paper<FrontEnd> {
    fun setProgrammer(programmer: FrontEnd) {
        _programmer = programmer
    }

    // Paper에게 책임을 위임했지만 Paper를 상속받는 구현체 클래스에서는 다운캐스팅이 일어나고 OCP가 위반된다.
    override fun setData(programmer: FrontEnd) {
        programmer.setLanguage(language)
        programmer.setLibrary(library)
    }
}

// 하지만 ServerClient와 같이 n개의 관계를 다루게 되는 케이스로는 Client와 같이 구현할 수 없다.
class ServerClient(
    private val server: Server,
    private val backendLanguage: Language,
    private val frontendLanguage: Language,
    private var _backendProgrammer: Programmer,
    private var _frontendProgrammer: Programmer
): Paper<T> {
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

    // Paper가 Programmer를 알게 하여 관계를 역전시키면
    // ServerClient는 Programmer와 n의 관계를 가진다.
    // 따라서 그 만큼의 경우를 다룰 책임이 생긴다.
    override fun setData(programmer: Programmer) {
        when(programmer){
            is FrontEnd -> {
                programmer.setLanguage(frontendLanguage)
            }
            is BackEnd -> {
                programmer.setLanguage(backendLanguage)
                programmer.setServer(server)
            }
        }
    }
}