package webserver;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadTest {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorsTest.class);

    private static AtomicInteger counter = new AtomicInteger(0);

    @Test
    void corePool1Request250KeepTime1Sec() throws Exception {
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(100);

        StopWatch sw = new StopWatch();
        sw.start();
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 250, 1, TimeUnit.SECONDS, queue);

        for (int i = 0; i < 250; i++) {
            executorService.execute(() -> {
                int idx = counter.addAndGet(1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                logger.info("Thread {}", idx);
            });
        }
        sw.stop();


        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.SECONDS);
        logger.info("Total Elaspsed: {}", sw.getTotalTimeSeconds());
    }

    @Test
    void corePool1Request250KeepTime1Min() throws Exception {
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(100);

        StopWatch sw = new StopWatch();
        sw.start();
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 250, 1, TimeUnit.MINUTES, queue);
        for (int i = 0; i < 250; i++) {
            executorService.execute(() -> {
                int idx = counter.addAndGet(1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                logger.info("Thread {}", idx);
            });
        }
        sw.stop();


        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.SECONDS);
        logger.info("Total Elaspsed: {}", sw.getTotalTimeSeconds());
    }

    @Test
    void corePool250Request250KeepTime1Sec() throws Exception {
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(100);

        StopWatch sw = new StopWatch();
        sw.start();
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(250, 250, 1, TimeUnit.SECONDS, queue);
        for (int i = 0; i < 250; i++) {
            executorService.execute(() -> {
                int idx = counter.addAndGet(1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                logger.info("Thread {}", idx);
            });
        }
        sw.stop();

        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.SECONDS);
        logger.info("Total Elaspsed: {}", sw.getTotalTimeSeconds());
    }

    @Test
    void corePool250Request250KeepTime1Min() throws Exception {
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(100);

        StopWatch sw = new StopWatch();
        sw.start();
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(250, 250, 1, TimeUnit.MINUTES, queue);
        for (int i = 0; i < 250; i++) {
            executorService.execute(() -> {
                int idx = counter.addAndGet(1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                logger.info("Thread {}", idx);
            });
        }
        sw.stop();

        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.SECONDS);
        logger.info("Total Elaspsed: {}", sw.getTotalTimeSeconds());
    }

    @Test
    void corePool1Request350KeepTime1Sec() throws Exception {
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(100);

        StopWatch sw = new StopWatch();
        sw.start();
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 250, 1, TimeUnit.SECONDS, queue);
        for (int i = 0; i < 350; i++) {
            executorService.execute(() -> {
                int idx = counter.addAndGet(1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                logger.info("Thread {}", idx);
            });
        }
        sw.stop();


        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.SECONDS);
        logger.info("Total Elaspsed: {}", sw.getTotalTimeSeconds());
    }

    @Test
    void corePool1Request350KeepTime1Min() throws Exception {
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(100);

        StopWatch sw = new StopWatch();
        sw.start();
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 250, 1, TimeUnit.MINUTES, queue);
        for (int i = 0; i < 350; i++) {
            executorService.execute(() -> {
                int idx = counter.addAndGet(1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                logger.info("Thread {}", idx);
            });
        }
        sw.stop();


        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.SECONDS);
        logger.info("Total Elaspsed: {}", sw.getTotalTimeSeconds());
    }

    @Test
    void corePool250Request350KeepTime1Sec() throws Exception {
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(100);

        StopWatch sw = new StopWatch();
        sw.start();
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(250, 250, 1, TimeUnit.SECONDS, queue);
        for (int i = 0; i < 350; i++) {
            executorService.execute(() -> {
                int idx = counter.addAndGet(1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                logger.info("Thread {}", idx);
            });
        }
        sw.stop();

        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.SECONDS);
        logger.info("Total Elaspsed: {}", sw.getTotalTimeSeconds());
    }

    @Test
    void corePool250Request350KeepTime1Min() throws Exception {
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(100);

        StopWatch sw = new StopWatch();
        sw.start();
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(250, 250, 1, TimeUnit.MINUTES, queue);
        for (int i = 0; i < 350; i++) {
            executorService.execute(() -> {
                int idx = counter.addAndGet(1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                logger.info("Thread {}", idx);
            });
        }
        sw.stop();

        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.SECONDS);
        logger.info("Total Elaspsed: {}", sw.getTotalTimeSeconds());
    }
}
