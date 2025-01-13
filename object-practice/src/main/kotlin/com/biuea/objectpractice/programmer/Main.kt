package com.biuea.objectpractice.programmer

class Main {
    fun main() {
        val director = Director()
        director.addProject(
            "여행사 A 프론트 개편",
            object: Client(
                Library("vueJS"),
                Language("kotlinJS"),

            ) {
                override fun run(): Set<Program> {
                    val frontEnd = object: FrontEnd<Client>() {
                        override fun setData(paper: Client) {
                            language = paper.language
                            library = paper.library
                        }
                    }
                    _programmer = frontEnd
                    return setOf(frontEnd.getProgram(this))
                }
            }
        )
        director.addProject(
            "xx은행 리뉴얼",
            object: ServerClient() {
                override fun run(): Set<Program> {
                    val frontEnd = object: FrontEnd<ServerClient>() {
                        override fun setData(paper: ServerClient) {
                            server = paper.server
                            language = paper.frontendLanguage
                        }
                    }
                    val backend = object: BackEnd<ServerClient>() {
                        override fun setData(paper: ServerClient) {
                            server = paper.server
                            language = paper.backendLanguage
                        }
                    }
                    _backendProgrammer = backend
                    _frontendProgrammer = frontEnd

                    return setOf(
                        frontEnd.getProgram(this),
                        backend.getProgram(this)
                    )
                }
            }
        )

        director.runProject("여행사 A 프론트 개편")
        director.runProject("xx은행 리뉴얼")
    }
}