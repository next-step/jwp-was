package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class ExecutorsTest {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorsTest.class);

    private static AtomicInteger counter = new AtomicInteger(0);
    private static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(10);

        String requestUri = "http://localhost:8080/index.html";

        StopWatch sw = new StopWatch();
        sw.start();
        for (int i = 0; i < 100; i++) {
            es.execute(() -> {
                int idx = counter.addAndGet(1);
                try {
                    ResponseEntity<String> responseEntity = restTemplate.getForEntity(new URI(requestUri), String.class);
                    assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                logger.info("Thread {}", idx);
            });
        }
        sw.stop();

        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);
        logger.info("Total Elaspsed: {}", sw.getTotalTimeSeconds());
    }
}

