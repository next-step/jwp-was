package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.StopWatch;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorsTest {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorsTest.class);

    private static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(100);

        StopWatch sw = new StopWatch();
        sw.start();
        for (int i = 0; i < 100; i++) {
            es.execute(() -> {
                int idx = counter.addAndGet(1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("Thread {}", idx);
            });
        }
        sw.stop();

        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);
        logger.info("Total Elaspsed: {}", sw.getTotalTimeSeconds());
    }

    @Test
    @DisplayName("풀 사이즈를 10, 스레드 대기 큐를 100으로 설정후 100개의 요청을 전송시 대기 큐에 90개가 쌓이는지 테스트")
    void threadWaitingTest() throws InterruptedException {
        int CORE_POOL_SIZE = 10;
        int MAXIMUM_POOL_SIZE = 10;
        long KEEP_ALIVE_TIME = 10L;
        int WORK_QUEUE_SIZE = 100;

        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(WORK_QUEUE_SIZE)
        );

        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.execute(() -> {
                int idx = counter.addAndGet(1);

                WebTestClient
                    .bindToServer()
                    .baseUrl("http://localhost:8080")
                    .build()
                    .get()
                    .uri("index.html");

                logger.info("queued task count = {}", threadPoolExecutor.getQueue().size());
                logger.info("Thread {} \n", idx);
            });
        }

        threadPoolExecutor.shutdown();
        threadPoolExecutor.awaitTermination(100, TimeUnit.SECONDS);
    }
}
