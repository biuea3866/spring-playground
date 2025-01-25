dependencies {
    implementation("org.springframework.kafka:spring-kafka")

    implementation(project(":table-application"))
}

tasks {
    bootJar {
        enabled = false
    }
}