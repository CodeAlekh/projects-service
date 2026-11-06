package io.github.codealekh.payments;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Local dev only (used by `./gradlew bootTestRun`): starts a throwaway Postgres
 * in Docker. @ServiceConnection wires its URL/username/password into the Spring
 * datasource automatically.
 */
@Configuration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:18-alpine"));
    }
}
