package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import webserver.response.FileResponse;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestTest.class);

    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/index.html", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("스레드 수가 서버의 MAX_THREAD_SIZE를 넘어간다")
    void request_over_thread() {
        RestTemplate restTemplate = new RestTemplate();

        IntStream.range(1, 250)
                .parallel()
                .forEach(n -> {
                    ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/index.html", String.class);
                    logger.info("스레드이름 : {}", Thread.currentThread().getName());
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                });
    }
}
