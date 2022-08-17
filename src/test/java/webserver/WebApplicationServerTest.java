package webserver;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

public class WebApplicationServerTest {

    private final String BASE_URL = "http://localhost:8080";

    @DisplayName("서버의 최대 Thread Pool 수만큼 요청을 보낸다.")
    @Test
    void request_maximum_threads() throws InterruptedException {
        int threadPoolSize = 250;
        CountDownLatch countDownLatch = new CountDownLatch(threadPoolSize);
        RestTemplate restTemplate = new RestTemplate();

        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        for (int count = 0; count < threadPoolSize; count++) {
            executorService.execute(() -> {
                restTemplate.getForEntity(BASE_URL, String.class);
                countDownLatch.countDown();
            });
        }
        boolean await = countDownLatch.await(10, TimeUnit.SECONDS);

        assertThat(countDownLatch.getCount()).isZero();
        assertThat(await).isTrue();
    }

    @DisplayName("서버의 최대 Thread Pool 수보다 더 많은 요청을 보낸다.")
    @Test
    void request_more_than_maximum_threads() throws InterruptedException {
        int threadPoolSize = 300;
        CountDownLatch latch = new CountDownLatch(threadPoolSize);
        RestTemplate restTemplate = new RestTemplate();

        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        for (int count = 0; count < threadPoolSize; count++) {
            executorService.execute(() -> restTemplate.getForEntity(BASE_URL, String.class));
        }
        boolean await = latch.await(10, TimeUnit.SECONDS);

        assertThat(latch.getCount()).isNotZero();
        assertThat(await).isFalse();
    }

}
