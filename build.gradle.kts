plugins {
	java
	id("org.springframework.boot") version "3.5.16"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "io.github.codealekh"
version = "0.0.1"
description = "Projects service"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	runtimeOnly("org.postgresql:postgresql")
	// Not tests: used only by `./gradlew bootTestRun` to start a local Postgres container
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:postgresql")
}

tasks.test {
	enabled = false
}

tasks.jar {
	enabled = false                      // don't produce the -plain.jar at all
}

tasks.bootJar {
	archiveFileName.set("projects-service.jar")
}
