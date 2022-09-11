package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {

    private static final String PORT = "8888";
    private static final String URL = "http://localhost:" + PORT;

    @DisplayName("서버의 스레드풀보다 적은 요청을 보낸다.")
    @Test
    void request_thread_pool_less() throws InterruptedException, ExecutionException {
        final int requestCount = 50;

        final CountDownLatch countDownLatch = new CountDownLatch(requestCount);
        final ExecutorService executorService = Executors.newFixedThreadPool(requestCount);
        final StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        final List<CompletableFuture<Void>> futures = IntStream.range(0, requestCount)
                .mapToObj(idx -> CompletableFuture.runAsync(() -> {
                    final RestTemplate restTemplate = new RestTemplate();
                    final ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8888/index.html", String.class);
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                    countDownLatch.countDown();
                }, executorService))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
        boolean await = countDownLatch.await(1000, TimeUnit.MILLISECONDS);
        stopWatch.stop();

        System.out.println("ExecutionTime: " + stopWatch.getTotalTimeSeconds());
        assertThat(countDownLatch.getCount()).isZero();
        assertThat(await).isTrue();
        assertThat(stopWatch.getTotalTimeSeconds()).isLessThanOrEqualTo(1);
    }

    @DisplayName("서버의 스레드풀보다 많은 요청을 보낸다.")
    @Test
    void request_thread_pool_over() throws InterruptedException {
        final int requestCount = 300;

        final CountDownLatch countDownLatch = new CountDownLatch(requestCount);
        final ExecutorService executorService = Executors.newFixedThreadPool(requestCount);
        final StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        final List<CompletableFuture<Void>> futures = IntStream.range(0, requestCount)
                .mapToObj(idx -> CompletableFuture.runAsync(() -> {
                    countDownLatch.countDown();
                    final RestTemplate restTemplate = new RestTemplate();
                    final ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8888/index.html", String.class);
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                }, executorService))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
        final boolean await = countDownLatch.await(10000, TimeUnit.MILLISECONDS);
        stopWatch.stop();

        System.out.println("ExecutionTime: " + stopWatch.getTotalTimeSeconds());
        assertThat(countDownLatch.getCount()).isZero();
        assertThat(await).isTrue();
        assertThat(stopWatch.getTotalTimeSeconds()).isLessThanOrEqualTo(1);
    }
}
