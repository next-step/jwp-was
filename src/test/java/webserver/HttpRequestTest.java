package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestTest.class);

    void setUpServer(String port, String poolSize, String queueSize) {
        Thread thread = new Thread(() -> {
            try {
                WebServer.main(new String[]{port, poolSize, queueSize});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }


    @DisplayName("한정된 스레드풀에서 요청을 처리할 수 없으면 RejectedExecutionException 발생")
    @Test
    void test_requests_exceeding_threadpoolSize() throws Exception {
        // given
        String port = "8080";
        String poolSize = "5";
        String queueSize = "10";
        setUpServer(port, poolSize, queueSize);

        int nThreads = 20;

        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        // when
        IntStream.range(0, nThreads).forEach(n -> {
            executorService.execute(() -> {
                RestTemplate restTemplate = new RestTemplate();
                String resourceUrl = "http://localhost:" + port;
                ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/index.html", String.class);

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            });
        });
        // then
        executorService.shutdown();
        executorService.awaitTermination(200, TimeUnit.SECONDS);
    }
}
