package webserver;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class HttpRequestTest {
    private static final int CORE_POOL_SIZE = 250;
    private static final int MAXIMUM_POOL_SIZE = 250;
    private static final int KEEP_ALIVE_TIME = 0;
    private static final int QUEUE_CAPACITY = 100;
    private static ThreadPoolExecutor threadPoolExecutor;

    @BeforeAll
    static void threadPoolSetup() {
        threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>(QUEUE_CAPACITY));
    }

    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("웹 서버의 최대 스레드 풀 + 대기 큐 사이즈 이하 요청 시 요청을 잘 처리한다.")
    void thread_pool_size_ok() {
        requestParallel(350);
    }

    @Test
    @DisplayName("웹 서버의 최대 스레드 풀 + 대기 큐 사이즈 초과 요청 시 예외가 발생한다.")
    void thread_pool_size_over() {
        assertThatThrownBy(() -> requestParallel(351)).isInstanceOf(RejectedExecutionException.class);
    }

    private void requestParallel(int requestCount) {
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