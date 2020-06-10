package webserver;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class WebServerTest {
    @Test
    void request_test() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080";
        IntStream.range(0, 100).forEach(n -> executorService.execute(() -> {
            ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/index.html", String.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }));

        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.SECONDS);
    }
}
