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

public class HttpRequestTest {
    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void request_resttemplate_with_thread() {
        ExecutorService executor = Executors.newFixedThreadPool(250);
        RestTemplate restTemplate = new RestTemplate();
        IntStream.range(0, 250).forEach(n -> executor.execute(() ->
                {
                    try {
                        restTemplate.getForEntity("http://localhost:8080", String.class);

                        executor.shutdownNow();
                        executor.awaitTermination(100, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
        );
    }
}
