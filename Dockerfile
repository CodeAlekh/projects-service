# Stage 0: development
FROM gradle:8.6-jdk17 AS dev
WORKDIR /app

# Stage 1: Build the application
FROM gradle:8.6-jdk17 AS build

WORKDIR /app

COPY src .
RUN gradle clean build -x test -Dsonar.gradle.skipCompile=true

# Stage 2: Create the final image
FROM amazoncorretto:17-al2023-headless as prod

WORKDIR /opt

# Copy the built JAR from the build stage
COPY --from=build /app/build/libs/projects-service.jar projects-service.jar

# Expose necessary ports
EXPOSE 8080
# JVM Flags can be adjusted as per your needs
ENTRYPOINT ["java", "-Xms512m", "-Xmx1024m", "-jar", "projects-service.jar"]
