package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class WebServerTest {
    private static final int CORE_POOL_SIZE = 100;
    private static final int MAXIMUM_POOL_SIZE = 250;
    private static final int KEEP_ALIVE_TIME = 0;
    private static final int WORK_QUEUE_CAPACITY = 100;
    private static final String resourceUrl = "http://localhost:8080/index.html";

    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
    }

    @DisplayName("최대 ThreadPool 사이즈와 대기 큐 사이즈까지 호출을 수용한다.")
    @Test
    void request_test() throws Exception {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>(WORK_QUEUE_CAPACITY));

        for (int i = 0; i < 350; i++) {
            executor.execute(new Thread(() -> {
                try {
                    request();
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
        }
    }

    @DisplayName("최대 ThreadPool 사이즈와 대기 큐 사이즈 넘어서 호출 시 에러 발생한다.")
    @Test
    void request_test2() throws Exception {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>(WORK_QUEUE_CAPACITY));

        assertThatThrownBy(() -> {
            for (int i = 0; i < 351; i++) {
                executor.execute(new Thread(() -> {
                    try {
                        request();
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }));
            }
        }).isInstanceOf(RejectedExecutionException.class);
    }

    void request() {
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
