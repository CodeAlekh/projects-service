package io.github.codealekh.payments;

import org.springframework.boot.SpringApplication;

/**
 * Local dev entry point: `./gradlew bootTestRun` starts the app together
 * with a Postgres container, so no local DB install or credentials needed.
 */
public class TestPaymentsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(PaymentsServiceApplication::main)
                .with(TestcontainersConfiguration.class)
                .run(args);
    }
}
