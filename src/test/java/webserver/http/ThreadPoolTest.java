package webserver.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ThreadPoolTest {
    private static final int CORE_POOL_SIZE = 250;
    private static final int MAX_POOL_SIZE = 250;
    private static final long KEEP_ALIVE_TIME = 0L;
    private static final int QUEUE_CAPACITY = 100;
    public static final String TEST_URL = "http://localhost:8080/index.html";
    private static ThreadPoolExecutor threadPoolExecutor;

    @BeforeAll
    static void setUp() {
        threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingDeque<>(QUEUE_CAPACITY));
    }

    @Test
    @DisplayName("스레드 풀 테스트")
    void threadPoolTest() {
        ResponseEntity<String> response = new RestTemplate().getForEntity(TEST_URL, String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    @DisplayName("스레드 풀 최대 값으로 테스트")
    void maxThreadPoolSizeTest() {
        executor(350);
    }

    @Test
    @DisplayName("스레드 풀 최대 값 초과 테스트")
    void maxThreadPoolSizeOverTest() {
        assertThatThrownBy(() -> executor(351))
                .isInstanceOf(RejectedExecutionException.class);
    }

    private void executor(int requestCount) {
        for (int i = 0; i < requestCount; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    threadPoolTest();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
