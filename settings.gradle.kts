rootProject.name = "spring-playground"

pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val kotlinJvmPluginVersion: String by settings
    val springDependencyManagementVersion: String by settings

    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }

    plugins {
        kotlin("jvm") version kotlinJvmPluginVersion
        id("org.jetbrains.kotlin.kapt") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
        id("org.springframework.boot") version springBootVersion
        id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion
    }
}

include(
    ":table-reserving-admin-api",
    ":table-reserving-api",
    ":table-reserving-application",
//    ":platform-domain",
//    ":platform-infra"
)

project(":table-reserving-admin-api").projectDir = file("table-reserving/table-reserving-admin-api")
project(":table-reserving-api").projectDir = file("table-reserving/table-reserving-api")
project(":table-reserving-application").projectDir = file("table-reserving/table-reserving-application")
//project(":platform-domain").projectDir = file("platform/platform-domain")
//project(":platform-infra").projectDir = file("platform/platform-infra")