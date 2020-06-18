package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.BindException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class WebServerTest {

    @Test
    @DisplayName("서버를 실행할 때 해당 포트를 이미 사용중인 경우 예외를 알려준다")
    void aleadyUsingPort() {
        final String[] args = new String[]{};

        runServer();
        final Throwable thrown = catchThrowable(() -> WebServer.main(args));

        assertThat(thrown)
                .isInstanceOf(BindException.class)
                .hasMessageContaining("Address already in use (Bind failed)");
    }

    @Test
    @DisplayName("서버에서 리소스를 정상적으로 가져올 수 있다")
    void requestOne() {
        final String resourceUrl = "http://localhost:8080/index.html";

        runServer();
        final ResponseEntity<String> response = request(resourceUrl);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private ResponseEntity<String> request(String resourceUrl) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(resourceUrl, String.class);
    }

    @Test
    @DisplayName("동시에 요청한 사용자 수가 쓰레드풀 사이즈, 대기열 사이즈를 합한 것과 같을 경우 모든 요청에 정상적으로 응답한다")
    void requestMaximum() {

        final String resourceUrl = "http://localhost:8080/index.html";
        final int requestCount = 10; // WebServer의 MAXIMUM_POOL_SIZE + MAXIMUM_QUEUE_SIZE

        runServer();
        final int responsedCount = request(resourceUrl, requestCount);

        assertThat(responsedCount).isEqualTo(requestCount);
    }

    @Test
    @DisplayName("동시에 요청한 사용자 수가 쓰레드풀 사이즈, 대기열 사이즈를 합한 것보다 클 경우 유실이 생긴다")
    void requestExceedMaximum() {

        final String resourceUrl = "http://localhost:8080/index.html";
        final int requestCount = 15; // WebServer의 MAXIMUM_POOL_SIZE + MAXIMUM_QUEUE_SIZE + 5

        runServer();
        final int responsedCount = request(resourceUrl, requestCount);

        assertThat(responsedCount).isLessThan(requestCount);
    }

    private void runServer() {
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(() -> {
            try {
                WebServer.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
            ignored.printStackTrace();
        }
        try {
            es.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {
            ignored.printStackTrace();
        }
    }

    private int request(String resourceUrl, int requestCount) {
        final AtomicInteger counter = new AtomicInteger();

        ExecutorService es = Executors.newFixedThreadPool(requestCount);

        IntStream.rangeClosed(1, requestCount).forEach(x -> {
            es.execute(() -> {
                ResponseEntity<String> response = request(resourceUrl);
                if (response.getStatusCode() == HttpStatus.OK) {
                    counter.getAndIncrement();
                }
            });

        });

        es.shutdown();
        try {
            es.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return counter.get();
    }


}
