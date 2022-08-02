package webserver;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

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
    @DisplayName("min/max Pool Size 와 Queue Size 조정을 통한 동작 출력")
    public void printExecuteWithMinMaxThreadPoolAndQueueSize() {
        /**
         * maxPoolSize 만큼 확장하는 것보다 Queue 채우는 작업이 우선
         * Queue 크기를 넘어선 작업들이 요청되었을 때만 maxPoolSize 만큼 확장
         */
        int minCorePoolSize = 2;
        int maximumPoolSize = 5;
        long keepAliveTime = 0L;
        TimeUnit unit = TimeUnit.MILLISECONDS;
        ArrayBlockingQueue queue = new ArrayBlockingQueue<>(10);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(minCorePoolSize, maximumPoolSize, keepAliveTime, unit, queue);

        Runnable r = () -> {
            try {
                logger.debug("Thread Name : {}", Thread.currentThread().getName());
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        for (int i=0; i<15; i++) {
            executor.execute(r);
            logger.debug("poolSize: {}, active: {}, queue: {}", executor.getPoolSize(), executor.getActiveCount(), executor.getQueue().size());
        }
    }

    @Test
    @DisplayName("한정된 자원을 통한 오류 출력")
    public void printErrorLimitResources() {
        int minCorePoolSize = 2;
        int maximumPoolSize = 5;
        long keepAliveTime = 0L;
        TimeUnit unit = TimeUnit.MILLISECONDS;
        ArrayBlockingQueue queue = new ArrayBlockingQueue<>(10);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(minCorePoolSize, maximumPoolSize, keepAliveTime, unit, queue);

        Runnable r = () -> {
            try {
                logger.debug("Thread Name : {}", Thread.currentThread().getName());
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        try {
            for (int i = 0; i < 16; i++) {
                executor.execute(r);
                logger.debug("poolSize: {}, active: {}, queue: {}", executor.getPoolSize(), executor.getActiveCount(), executor.getQueue().size());
            }
        } catch (RejectedExecutionException e) {
            logger.error(e.getMessage());
        }
    }
}

