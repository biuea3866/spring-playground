FROM gradle:8-jdk21-alpine as builder

WORKDIR /app

#COPY gradlew /app/
#COPY gradle /app/gradle/
#COPY build.gradle.kts /app/
#COPY settings.gradle.kts /app/
#COPY gradle.properties /app/
#
#COPY table-reservation/table-auth/build.gradle.kts /app/table-auth/
#COPY table-reservation/table-domain/build.gradle.kts /app/table-domain/

COPY . .

RUN chmod +x gradlew
RUN ./gradlew :table-auth:bootJar -PdockerBuild=true -x test --stacktrace

# deploy
FROM openjdk:17-jdk-slim

COPY --from=builder /app/table-reservation/table-auth/build/libs/table-auth.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]