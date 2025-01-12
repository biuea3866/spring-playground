package com.biuea.objectpractice.programmer

class Director(
    private val projects: HashMap<String, Paper>
) {
    fun addProject(name: String, paper: Paper) {
        projects[name] = paper
    }

    fun runProject(name: String) {
        val paper = projects[name] ?: throw IllegalArgumentException("No project named $name")

        // 다운캐스팅을 하게 되면 ocp를 위반하게 된다.
        // if를 잘못 사용하게 되면 컴파일, 런타임 중에 오류를 찾지 못하고 버그를 만들어낸다.
        // 다운캐스팅은 컨텍스트 오류를 유발하며 일일이 어떤 케이스에 어떤 결과값이 나타나는지 찾아야한다.
        when(paper) {
            is ServerClient -> {
                val project = paper
                val frontend = FrontEnd()
                val backend = BackEnd()

                project.setBackEndProgrammer(backend)
                project.setFrontEndProgrammer(frontend)

                val client = frontend.makeProgram(project)
                val server = backend.makeProgram(project)

                deploy(name, client, server)
            }
            is Client -> {
                val project = paper
                val frontend = FrontEnd()

                project.setProgrammer(frontend)

                deploy(name, frontend.makeProgram(project))
            }
        }
    }

    private fun deploy(projectName: String, program: Set<Program>) {}
}