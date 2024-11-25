dependencies {
    implementation("org.springframework:spring-tx")
}

tasks {
    bootJar {
        enabled = false
    }

    register("prepareKotlinBuildScriptModel"){}
}