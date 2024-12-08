import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.jpa")
    id("org.springframework.boot")
    id("org.jetbrains.kotlin.plugin.spring")
    id("io.spring.dependency-management")
}

group = "com.biuea"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks {
    bootJar {
        enabled = false
    }

    jar {
        enabled = true
    }
}

allprojects {
    group = "com.biuea"
    version = "1.0.0"

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

subprojects {
    val kotlinVersion: String by project
    val coroutinesVersion: String by project
    val springBootVersion: String by project
    val junitJupiterVersion: String by project
    val logbackContribVersion: String by project
    val retrofitVersion: String by project
    val okHttpVersion: String by project
    val jacksonVersion: String by project
    val jakartaValidationApiVersion: String by project
    val fixtureMonkeyVersion: String by project
    val dataFakerVersion: String by project
    val mockitoKotlinVersion: String by project

    apply(plugin = "kotlin")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")

    tasks {
        withType<Test> {
            useJUnitPlatform()
        }

        compileKotlin {
            kotlinOptions.jvmTarget = "17"
        }

        compileTestKotlin {
            kotlinOptions.jvmTarget = "17"
        }

        allOpen {
            annotations(
                "jakarta.persistence.Entity",
                "jakarta.persistence.MappedSuperclass",
                "jakarta.persistence.Embeddable"
            )
        }
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-security")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))

        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$coroutinesVersion")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

        implementation("io.jsonwebtoken:jjwt-api:0.11.5")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }
}
