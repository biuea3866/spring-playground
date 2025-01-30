package com.biuea.table.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackages = ["com.biuea.table"])
@SpringBootApplication
class TableAuthApplication

fun main(args: Array<String>) {
    runApplication<TableAuthApplication>(*args)
}
