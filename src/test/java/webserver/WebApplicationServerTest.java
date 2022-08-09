package webserver;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class WebApplicationServerTest {

    @Test
    void name() throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();

        var executorService = Executors.newFixedThreadPool(10);

        var futures = IntStream.range(0, 10)
            .mapToObj(it -> executorService.submit(
                () -> restTemplate.getForEntity("http://localhost:8080/index.html", String.class)))
            .collect(Collectors.toList());

        for (Future<ResponseEntity<String>> future : futures) {
            try {
                future.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}