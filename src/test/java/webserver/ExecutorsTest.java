package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ExecutorsTest {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorsTest.class);

    private static AtomicInteger counter = new AtomicInteger(0);
    private static AtomicInteger counter2 = new AtomicInteger(0);

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


        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 100, 1, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<>(5));
        ThreadPoolExecutor threadPoolExecutor2 = new ThreadPoolExecutor(10, 100, 1, TimeUnit.MICROSECONDS, new SynchronousQueue<>());
        IntStream.range(0, 30).forEach(a -> threadPoolExecutor.execute(() -> {
            int i = counter2.addAndGet(1);
            logger.info(Thread.currentThread().toString() + "===" + i);
        }));

        IntStream.range(0, 30).forEach(a -> threadPoolExecutor2.execute(() -> {
            int i = counter2.addAndGet(1);
            logger.info(Thread.currentThread().toString() + "===" + i);
        }));
    }
}

