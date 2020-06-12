package webserver;

import org.junit.jupiter.api.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRejectTest {

    @Test
    void test() {
        final int CORE_POOL_SIZE = 5;
        final int MAX_POOL_SIZE = 5;
        final int KEEP_ALIVE_TIME = 1;
        final int BLOCKING_QUEUE_SIZE = 1;

        final ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(
                        CORE_POOL_SIZE,
                        MAX_POOL_SIZE,
                        KEEP_ALIVE_TIME,
                        TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<>(BLOCKING_QUEUE_SIZE)
                );

        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // Policy 지정

        try {
            for (int i = 0; i < 100; i++) {
                Runnable runnable = () -> {
                    final ThreadPoolExecutor pool = threadPoolExecutor;
                    System.out.println("Thread count: " + pool.getPoolSize() + ", current thread: " + Thread.currentThread().getName());
                };
                threadPoolExecutor.execute(runnable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
