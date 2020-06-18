package webserver;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "https://edu.nextstep.camp";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/c/4YUvqn9V", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(400);

        IntStream.range(0, 1000).forEach(n ->

                executor.execute(() -> {
                    RestTemplate restTemplate = new RestTemplate();
                    String resourceUrl = "http://localhost:8080";
                    ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/index.html", String.class);
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                })
        );
    }
}
