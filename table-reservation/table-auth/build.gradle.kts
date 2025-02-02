import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("plugin.spring")
    kotlin("jvm") version "1.9.0"
}

java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

tasks.withType<KotlinJvmCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    val springDocVersion: String by project

    implementation("org.springdoc:springdoc-openapi-ui:$springDocVersion")
    implementation("org.springdoc:springdoc-openapi-kotlin:$springDocVersion")

    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation(project(":table-domain"))
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    register("prepareKotlinBuildScriptModel"){}

    bootJar {
        enabled = true
    }

    jar {
        enabled = true
    }

    withType<BootJar> {
        archiveFileName.set("table-auth.jar")
        mainClass.set("com.biuea.table.auth.TableAuthApplicationKt")
    }
}
