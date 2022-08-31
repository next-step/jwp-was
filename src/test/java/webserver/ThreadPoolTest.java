package webserver;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ThreadPoolTest {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolTest.class);

    private static final int CORE_POOL_SIZE = 250;
    private static final int MAX_POOL_SIZE = 250;
    private static final long KEEP_ALIVE_TIME = 0L;
    private static final int QUEUE_CAPACITY = 100;
    private static ThreadPoolExecutor threadPoolExecutor;

    @BeforeAll
    static void setUp() {
        threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>(QUEUE_CAPACITY));
    }

    @Test
    void request_resttemplate() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8084/index.html", String.class);
            assertThat(response.getStatusCode().is2xxSuccessful()).isEqualTo(true);
        } catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
        }
    }

    @Test
    void success() {
        threadRequest(350);
    }

    @Test
    void failure() {
        assertThatThrownBy(() -> threadRequest(351)).isInstanceOf(RejectedExecutionException.class);
    }

    private void threadRequest(int requestCount) {
        for (int i = 0; i < requestCount; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    request_resttemplate();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
