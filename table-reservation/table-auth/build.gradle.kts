import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("plugin.spring")
    kotlin("jvm") version "2.1.0"
}

java.sourceCompatibility = JavaVersion.VERSION_21
java.targetCompatibility = JavaVersion.VERSION_21

tasks.withType<KotlinJvmCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    val springDocVersion: String by project

    implementation("org.springdoc:springdoc-openapi-ui:$springDocVersion")
    implementation("org.springdoc:springdoc-openapi-kotlin:$springDocVersion")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation(project(":table-domain"))
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "21"
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
