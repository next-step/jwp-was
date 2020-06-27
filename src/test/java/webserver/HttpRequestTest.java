package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestTest.class);
    private ExecutorService CLIENT;
    private ExecutorService SERVER;

    AtomicBoolean runServer(String port, String poolSize, String queueSize) {
        AtomicBoolean hasOccuredRejectedExecutionException = new AtomicBoolean(false);

        SERVER = Executors.newSingleThreadExecutor();
        SERVER.execute(() -> {
            try {
                WebServer.main(new String[]{port, poolSize, queueSize});
            } catch (RejectedExecutionException e) {
                hasOccuredRejectedExecutionException.set(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return hasOccuredRejectedExecutionException;
    }

    List<Future<HttpStatus>> runClient(String port, int nThreads) {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:" + port;

        CLIENT = Executors.newFixedThreadPool(nThreads);
        return IntStream.range(0, nThreads).mapToObj(n ->
                CLIENT.submit(() -> {
                    ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/index.html", String.class);
                    return response.getStatusCode();
                })
        ).collect(Collectors.toList());
    }

    @DisplayName("한정된 스레드풀에서 요청을 처리할 수 없으면 RejectedExecutionException 발생")
    @Test
    void test_requests_exceeding_threadpoolSize() throws Exception {
        // given
        String port = "8080";
        String poolSize = "5";
        String queueSize = "1";

        // when
        AtomicBoolean hasOccuredRejectedExecutionException = runServer(port, poolSize, queueSize);

        int nThreads = 100;
        runClient(port, nThreads);

        SERVER.shutdown();
        SERVER.awaitTermination(3, TimeUnit.SECONDS);

        CLIENT.shutdown();
        CLIENT.awaitTermination(3, TimeUnit.SECONDS);

        // then
        assertThat(hasOccuredRejectedExecutionException.get()).isTrue();
    }

    @DisplayName("스레드풀이 예외없이 정상적으로 모든 요청 처리")
    @Test
    void test_successful_processing_requests() throws InterruptedException, ExecutionException {
        // given
        String port = "8080";
        String poolSize = "20";
        String queueSize = "10";

        // when
        AtomicBoolean hasOccuredRejectedExecutionException = runServer(port, poolSize, queueSize);

        int nThreads = 20;
        List<Future<HttpStatus>> futures = runClient(port, nThreads);

        SERVER.shutdown();
        SERVER.awaitTermination(3, TimeUnit.SECONDS);

        CLIENT.shutdown();
        CLIENT.awaitTermination(3, TimeUnit.SECONDS);

        // then
        for (Future<HttpStatus> result : futures) {
            assertThat(result.get()).isEqualTo(HttpStatus.OK);
        }
        assertThat(hasOccuredRejectedExecutionException.get()).isFalse();
    }
}
