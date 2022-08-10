package webserver;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorsTest.class);
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    @Test
    @Disabled
    void request_resttemplate() {
        ResponseEntity<String> response = REST_TEMPLATE.getForEntity("http://localhost:8080/index.html", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void request_resttemplate_동시접속테스트_허용된쓰레드풀갯수를초과했을때대기한다() throws InterruptedException {
        int requestCount = 450;

        ExecutorService es = Executors.newFixedThreadPool(requestCount);

        AtomicInteger counter = new AtomicInteger(0);
        StopWatch sw = new StopWatch();
        sw.start();

        List<CompletableFuture<Void>> completableFutures = IntStream.rangeClosed(1, requestCount)
                .mapToObj(ignored -> CompletableFuture.runAsync(() -> {
                    ResponseEntity<String> response = REST_TEMPLATE.getForEntity("http://localhost:8080/index.html", String.class);
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                    LOGGER.info("idx: {}, httpStatus: {}", counter.addAndGet(1), response.getStatusCode().name());
                }, es))
                .collect(Collectors.toList());

        completableFutures.stream().map(CompletableFuture::join);

        sw.stop();

        es.shutdown();
        es.awaitTermination(1, TimeUnit.SECONDS);

        assertThat(counter.get()).isLessThanOrEqualTo(requestCount);
        LOGGER.info("Total Count: {}", counter.get());
        LOGGER.info("Total Elaspsed: {}", sw.getTotalTimeSeconds());
    }
}
