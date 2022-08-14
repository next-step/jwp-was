package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorsTest.class);
    private static final AtomicInteger counter = new AtomicInteger(0);

    @Test
    void request_resttemplate() {
        logger.info("start_request_rest_template");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/index.html", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        logger.info("end_request_rest_template");
    }

    @DisplayName("rest template을 통해 threadPool이 5로 되어있는 환경에 10개의 스레드 요청을 한다.")
    @Test
    void executeMultiRequest() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        Future<?> future = null;
        for (int i = 0; i < 100; i++) {
            future = executorService.submit(() -> {
                int idx = counter.addAndGet(1);
                request_resttemplate();
                logger.info("Thread {}", idx);
            });
        }
        future.get();
        executorService.shutdown();
    }
}
