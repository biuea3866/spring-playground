import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

tasks.withType<KotlinJvmCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    val springDocVersion: String by project
    val springCloudApiGatewayVersion: String by project

    implementation("org.springdoc:springdoc-openapi-ui:$springDocVersion")
    implementation("org.springdoc:springdoc-openapi-kotlin:$springDocVersion")

    implementation("org.springframework.cloud:spring-cloud-starter-gateway:$springCloudApiGatewayVersion")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation(project(":table-reserving-application"))
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    register("prepareKotlinBuildScriptModel"){}
}
