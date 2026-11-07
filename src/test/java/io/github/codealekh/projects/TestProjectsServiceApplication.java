package io.github.codealekh.projects;

import org.springframework.boot.SpringApplication;

/**
 * Local dev entry point: `./gradlew bootTestRun` starts the app together
 * with a Postgres container, so no local DB install or credentials needed.
 */
public class TestProjectsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(ProjectsServiceApplication::main)
                .with(TestcontainersConfiguration.class)
                .run(args);
    }
}
