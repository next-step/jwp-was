package webserver.threadPool;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import testUtils.PortNumberProvider;
import webserver.WebServerExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ThreadPoolTest {

    @Test
    @DisplayName("동시에 요청한 사용자 수가 쓰레드풀 사이즈, 대기열 사이즈를 합한 것과 같을 경우 모든 요청에 정상적으로 응답한다")
    void requestMaximum() {
        // given
        final int port = PortNumberProvider.fetchPortNumber();
        final int maximumPoolSize = 5;
        final int maximumQueueSize = 5;
        final int requestCount = maximumPoolSize + maximumQueueSize;

        final String resourceUrl = "http://localhost:" + port + "/index.html";
        final String[] args = new String[]{String.valueOf(port), null, String.valueOf(maximumPoolSize), null, String.valueOf(maximumQueueSize)};

        // when
        WebServerExecutor.execute(args);
        final int responsedCount = request(resourceUrl, requestCount);

        // then
        assertThat(responsedCount).isEqualTo(requestCount);
    }

    @Test
    @DisplayName("동시에 요청한 사용자 수가 쓰레드풀 사이즈, 대기열 사이즈를 합한 것보다 클 경우 요청 중 일부는 응답을 받지 못한다")
    void requestExceedMaximum() {
        // given
        final int port = PortNumberProvider.fetchPortNumber();
        final int maximumPoolSize = 5;
        final int maximumQueueSize = 5;
        final int requestCount = 5 + maximumPoolSize + maximumQueueSize;

        final String resourceUrl = "http://localhost:" + port + "/index.html";
        final String[] args = new String[]{String.valueOf(port), null, String.valueOf(maximumPoolSize), null, String.valueOf(maximumQueueSize)};

        // when
        WebServerExecutor.execute(args);
        final int responsedCount = request(resourceUrl, requestCount);

        // then
        assertThat(responsedCount).isLessThan(requestCount);
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

    private ResponseEntity<String> request(String resourceUrl) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(resourceUrl, String.class);
    }

}
