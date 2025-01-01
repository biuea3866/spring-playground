package com.biuea.objectpractice.example

// worker는 runnable의 형을 가지고 있다.
// runnable을 포인트로써 runnable의 기능을 사용할 수 있다.
open class Worker: Runnable {
    override fun run() {
        println("Worker is running")
    }

    fun print() = println(this.run())
}

class HardWorker: Worker() {
    override fun run() {
        println("HardWorker is running")
    }
}

fun execute() {
    // Worker의 인스턴스가 생성된다.
    // 단, Runnable 포인터를 가질 수 있다.
    var worker: Runnable = Worker()
    println(worker.run())

    worker = HardWorker()
    println(worker.print())
}