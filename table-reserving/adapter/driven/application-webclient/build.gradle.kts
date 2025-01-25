dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation(project(":table-application"))
    implementation(project(":table-reservation-domain"))
}

tasks {
    bootJar {
        enabled = false
    }
}