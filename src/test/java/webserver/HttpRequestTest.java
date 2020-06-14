package webserver;

import org.junit.jupiter.api.DisplayName;
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
        String resourceUrl = "http://localhost:8080";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/index.html", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("설정되어 있는 thread의 개수(100)보다 더 많은 thread(1000)가 발생했을떄")
    void threadExecuteMorePoolSizeTest() {

        IntStream.range(0, 1000).forEach(n -> {
                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/index.html", String.class);
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                });
    }
}
