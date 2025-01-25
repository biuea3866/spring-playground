dependencies {
    // infrastructure dependency
    // querydsl + jpa + mysql version
    val querydslAptVersion: String by project
    val hibernateJpaVersion: String by project
    val hibernateTypesVersion: String by project
    val commonsPoolVersion: String by project
    val mysqlVersion: String by project
    val hypersistenceUtilsVersion: String by project
    val testContainersVersion: String by project

    // querydsl + jpa + mysql
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    implementation("org.apache.commons:commons-pool2:$commonsPoolVersion")
    implementation("io.hypersistence:hypersistence-utils-hibernate-62:$hypersistenceUtilsVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    kapt("com.querydsl:querydsl-apt:$querydslAptVersion")
    kapt("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:$hibernateJpaVersion")

    runtimeOnly("mysql:mysql-connector-java:$mysqlVersion")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

    testImplementation("org.testcontainers:junit-jupiter:$testContainersVersion")
    testImplementation("org.testcontainers:mysql:$testContainersVersion")

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.2")

    tasks {
        bootJar {
            enabled = false
        }
    }

    implementation(project(":table-application"))
    implementation(project(":table-reservation-domain"))
    implementation(project(":table-user-domain"))
    implementation(project(":table-restaurant-domain"))
}

tasks {
    bootJar {
        enabled = false
    }
}