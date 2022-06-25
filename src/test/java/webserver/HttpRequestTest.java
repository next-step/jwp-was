package webserver;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class HttpRequestTest {

    @Test
    void request_success() throws InterruptedException {
        //given
        int count = 250;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        RestTemplate restTemplate = new RestTemplate();
        ExecutorService executorService = Executors.newFixedThreadPool(count);

        //when
        IntStream.range(0, count).forEach(i ->
                executorService.execute(() -> {
                    restTemplate.getForEntity("http://localhost:8080/index.html", String.class);
                    countDownLatch.countDown();
                }));
        countDownLatch.await(5, TimeUnit.SECONDS);

        //then
        assertThat(countDownLatch.getCount()).isEqualTo(0);
    }

    @Test
    void request_fail() throws InterruptedException {
        //given
        int count = 500;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        RestTemplate restTemplate = new RestTemplate();
        ExecutorService executorService = Executors.newFixedThreadPool(count);

        //when
        IntStream.range(0, count).forEach(i ->
                executorService.execute(() -> {
                    restTemplate.getForEntity("http://localhost:8080/index.html", String.class);
                    countDownLatch.countDown();
                }));
        countDownLatch.await(10, TimeUnit.SECONDS);

        //then
        assertThat(countDownLatch.getCount()).isNotEqualTo(0);
    }
}
