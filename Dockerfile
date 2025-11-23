
FROM gradle:8.7-jdk17 AS build


WORKDIR /app


COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle


RUN ./gradlew dependencies --no-daemon || true


COPY src ./src


RUN ./gradlew bootJar --no-daemon


FROM eclipse-temurin:17-jdk-alpine


WORKDIR /app


COPY --from=build /app/build/libs/*.jar app.jar


EXPOSE 8080


ENTRYPOINT ["java", "-jar", "app.jar"]
