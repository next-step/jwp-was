package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {

    @DisplayName("스레드로컬 테스트!")
    @Test
    void thread_local() throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(1);

        for (int i = 0; i < 10; i++) {
            final ThreadLocalRunner runner = new ThreadLocalRunner();
            es.execute(runner);
        }

        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);
    }
}
