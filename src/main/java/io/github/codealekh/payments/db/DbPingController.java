package io.github.codealekh.payments.db;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/db")
public class DbPingController {

    private final JdbcTemplate jdbcTemplate;

    public DbPingController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/ping")
    public ResponseEntity<DbPingResponse> ping() {
        long start = System.nanoTime();
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            long latencyMs = (System.nanoTime() - start) / 1_000_000;
            return ResponseEntity.ok(new DbPingResponse("UP", result, latencyMs));
        } catch (DataAccessException e) {
            long latencyMs = (System.nanoTime() - start) / 1_000_000;
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new DbPingResponse("DOWN", null, latencyMs));
        }
    }

    public record DbPingResponse(String status, Integer result, long latencyMs) {
    }
}
