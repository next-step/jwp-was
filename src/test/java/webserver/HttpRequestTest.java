package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;

public class HttpRequestTest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestTest.class);

    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("WAS의 ThreadPool 수만큼 요청한다.")
    @Test
    void concurrentRequest1() throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(100);
        RestTemplate restTemplate = new RestTemplate();
        AtomicInteger counter = new AtomicInteger(0);

        StopWatch sw = new StopWatch();
        sw.start();

        for (int i = 0; i < 100; i++) {
            es.execute(() -> {
                int idx = counter.addAndGet(1);
                restTemplate.getForEntity("http://localhost:8080", String.class);
                logger.info("Thread {}", idx);
            });
        }
        sw.stop();

        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);
        logger.info("Total Elaspsed: {}", sw.getTotalTimeSeconds());
    }

    @DisplayName("WAS의 ThreadPool 수보다 많이 요청한다.")
    @Test
    void concurrentRequest2() throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(200);
        RestTemplate restTemplate = new RestTemplate();
        AtomicInteger counter = new AtomicInteger(0);

        StopWatch sw = new StopWatch();
        sw.start();

        for (int i = 0; i < 100; i++) {
            es.execute(() -> {
                int idx = counter.addAndGet(1);
                restTemplate.getForEntity("http://localhost:8080", String.class);
                logger.info("Thread {}", idx);
            });
        }
        sw.stop();

        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);
        logger.info("Total Elaspsed: {}", sw.getTotalTimeSeconds());
    }
}
