# Stage 1: Build the application
FROM eclipse-temurin:21.0.13_9-jdk-ubi9-minimal AS build

WORKDIR /app

# Build files first: these rarely change, so the dependency layer below stays cached
COPY gradlew settings.gradle.kts build.gradle.kts ./
COPY gradle/ gradle/
RUN ./gradlew dependencies --no-daemon

# Source last: editing code only invalidates from here down
COPY src/ src/
RUN ./gradlew bootJar --no-daemon

# Stage 2: Create the final image
FROM eclipse-temurin:21.0.13_9-jre-ubi9-minimal AS prod

# Non-root user with a fixed numeric UID (Kubernetes runAsNonRoot can only verify numeric UIDs)
RUN useradd --uid 10001 --no-create-home --shell /sbin/nologin app

WORKDIR /opt

# Copy the built JAR from the build stage
COPY --from=build --chown=10001:10001 /app/build/libs/projects-service.jar projects-service.jar

USER 10001

# Expose necessary ports
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "projects-service.jar"]
