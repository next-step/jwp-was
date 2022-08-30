package webserver;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestTest {

    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

    @BeforeAll
    static void setup() {
        EXECUTOR.execute(() -> {
            try {
                WebApplicationServer.main(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @AfterAll
    static void teardown() {
        EXECUTOR.shutdown();
    }

    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/index.html", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("설정한 스레드갯수보다 더 많은 요청이 들어왔을 경우 테스트")
    void threadPoolTest() throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(10);

        StopWatch sw = new StopWatch();
        sw.start();
        for (int i = 0; i < 100; i++) {
            es.execute(() -> {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getForEntity("http://localhost:8080/index.html", String.class);
            });
        }

        sw.stop();
        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);
    }
}
