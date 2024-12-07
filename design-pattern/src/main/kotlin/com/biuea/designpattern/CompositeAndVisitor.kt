package com.biuea.designpattern

// sealed class는 상속을 제한하는 클래스이다.
sealed class FileSystem(filename: String) {
    // 함수의 형을 정의하기 위해
    fun interface Visitor: (FileSystem) -> Unit
    @PublishedApi internal var parent: Folder? = null
    var name = filename
    private set

    fun rename(newName: String) {
        parent?.children?.forEach {
            if (it.name == newName) {
                throw IllegalArgumentException("File already exists")
            }

            name = newName
        }
    }

    // 컴포지트 메서드
    // 모든 구성 요소들은 invoke 메서드를 가지고 있다.
    abstract operator fun invoke(visitor: Visitor)
}

class Folder(name: String): FileSystem(name) {
    private var _children: ArrayList<FileSystem>? = null
    val children get() = _children ?: emptyList()

    // invoke 메서드를 구현하여 순회를 visitor에게 위임
    override fun invoke(visitor: Visitor) {
        visitor(this)
        // 구성에게 똑같은 일을 시킨다
        _children?.forEach { it.invoke(visitor) }
    }

    fun add(vararg child: FileSystem): Folder {
        (_children?: arrayListOf<FileSystem>().also { _children = it }).addAll(child)
        child.forEach { it.parent = this }
        return this
    }

    fun remove(vararg child: FileSystem) {
        if (children.isEmpty()) return
        _children?.removeAll(child.toSet())
    }
}

class File(name: String): FileSystem(name) {
    override fun invoke(visitor: Visitor) {
        visitor(this)
    }
}

fun main() {
    val root = Folder("root").add(
        File("file1"),
        File("file2"),
        Folder("folder1").add(
            File("file3"),
            File("file4"),
            Folder("folder2").add(
                File("file5"),
                File("file6")
            )
        )
    )

    root {
        // visitor가 할 일
        println("${it.name}: ${if(it is Folder) "Folder" else "File"}")
    }

    root(object: FileSystem.Visitor {
        var isSelf = false

        override fun invoke(file: FileSystem) {
            if (!isSelf) {
                isSelf = true
                return
            }
            file.parent?.remove(file)
        }
    })
}