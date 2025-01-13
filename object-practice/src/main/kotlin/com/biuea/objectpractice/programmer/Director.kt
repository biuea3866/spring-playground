package com.biuea.objectpractice.programmer

class Director(
    private val projects: HashMap<String, Paper> = hashMapOf()
) {
    fun addProject(name: String, paper: Paper) {
        projects[name] = paper
    }

    fun runProject(name: String) {
        val paper = projects[name] ?: throw IllegalArgumentException("No project named $name")

        // 다운캐스팅을 하게 되면 ocp를 위반하게 된다.
        // if를 잘못 사용하게 되면 컴파일, 런타임 중에 오류를 찾지 못하고 버그를 만들어낸다.
        // 다운캐스팅은 컨텍스트 오류를 유발하며 일일이 어떤 케이스에 어떤 결과값이 나타나는지 찾아야한다.
        // 최상위 client로 밀어내고, client에서 if문에 해당하는 만큼 하드코딩을 통하여 분기를 없앨 수 있다.
        // 분기를 없앰으로써 lsp와 ocp를 지킬 수 있다.

        // 다만 director 수준에서 분기가 일어나기 때문에 OCP를 위반하게 된다.
        // 하여 공통적인 부분을 추상화할 필요가 있다.
        // 추상화의 결과는 Program 배열을 던져주는 Paper interface임.
        // paper에게 책임을 위임하고, paper는 Program을 만들어내는 책임을 가진다.
        deploy(name, paper.run())
    }

    private fun deploy(projectName: String, program: Set<Program>) {}
}

class Program

class Server(val name: String)

class Language(val name: String)

class Library(val name: String)