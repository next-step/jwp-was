package webserver;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolConfig {

    private static final int DEFAULT_THREAD_COUNT = 250;
    private static final int DEFAULT_QUEUE_SIZE = 100;
    private static final long DEFAULT_KEEP_ALIVE_TIME = 0L;

    private final WebApplicationConfig webApplicationConfig;

    public ThreadPoolConfig(WebApplicationConfig webApplicationConfig) {
        this.webApplicationConfig = webApplicationConfig;
    }

    public ExecutorService create() {
        var queue = new LinkedBlockingQueue<Runnable>(DEFAULT_QUEUE_SIZE);

        if (Objects.nonNull(webApplicationConfig.getThreadPoolSize())) {
            var maximumPoolSize = webApplicationConfig.getThreadPoolSize();
            return new ThreadPoolExecutor(maximumPoolSize, maximumPoolSize, DEFAULT_KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, queue);
        }
        return new ThreadPoolExecutor(DEFAULT_THREAD_COUNT, DEFAULT_THREAD_COUNT, 0L, TimeUnit.MILLISECONDS, queue);
    }
}
