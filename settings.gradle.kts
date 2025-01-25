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
    ":table-reserving-authn-api",
    ":table-reserving-gateway",
    ":table-user-domain",
    ":table-restaurant-domain",
    ":table-reservation-domain",
    ":table-payment-domain",
    ":table-reservation-mysql",
    ":table-application",
    ":table-application-webclient",
    ":table-reservation-kafka",
    ":object-practice",
    ":jpa-application"
//    ":platform-domain",
//    ":platform-infra"
)

project(":table-application-webclient").projectDir = file("table-reserving/adapter/driven/application-webclient")
project(":table-reservation-mysql").projectDir = file("table-reserving/adapter/driven/reservation-mysql")
project(":table-reservation-kafka").projectDir = file("table-reserving/adapter/driven/reservation-kafka")
project(":table-application").projectDir = file("table-reserving/core/table-application")
project(":table-payment-domain").projectDir = file("table-reserving/core/payment-domain")
project(":table-user-domain").projectDir = file("table-reserving/core/user-domain")
project(":table-reservation-domain").projectDir = file("table-reserving/core/reservation-domain")
project(":table-restaurant-domain").projectDir = file("table-reserving/core/restaurant-domain")
project(":table-reserving-admin-api").projectDir = file("table-reserving/table-reserving-admin-api")
project(":table-reserving-api").projectDir = file("table-reserving/table-reserving-api")
project(":table-reserving-application").projectDir = file("table-reserving/table-reserving-application")
project(":table-reserving-authn-api").projectDir = file("table-reserving/table-reserving-authn-api")
project(":table-reserving-gateway").projectDir = file("table-reserving/table-reserving-gateway")
project(":object-practice").projectDir = file("object-practice")
project(":jpa-application").projectDir = file("jpa-application")
//project(":platform-domain").projectDir = file("platform/platform-domain")
//project(":platform-infra").projectDir = file("platform/platform-infra")