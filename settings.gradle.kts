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

val isDockerBuild = (startParameter.projectProperties["dockerBuild"]?: "false").toBoolean()

if (isDockerBuild) {
    include(
        ":table-domain",
        ":table-auth",
        ":table-gateway"
    )
} else {
    include(
        ":object-practice",
        ":jpa-application",
        ":table-domain",
        ":table-auth",
        ":table-gateway",
        ":design-pattern"
//    ":platform-domain",
//    ":platform-infra"
    )
}

if (isDockerBuild) {
    project(":table-gateway").projectDir = file("table-reservation/table-gateway")
    project(":table-auth").projectDir = file("table-reservation/table-auth")
    project(":table-domain").projectDir = file("table-reservation/table-domain")
} else {
    project(":table-gateway").projectDir = file("table-reservation/table-gateway")
    project(":table-auth").projectDir = file("table-reservation/table-auth")
    project(":table-domain").projectDir = file("table-reservation/table-domain")
    project(":object-practice").projectDir = file("object-practice")
    project(":jpa-application").projectDir = file("jpa-application")
    project(":design-pattern").projectDir = file("design-pattern")
}
//project(":platform-domain").projectDir = file("platform/platform-domain")
//project(":platform-infra").projectDir = file("platform/platform-infra")