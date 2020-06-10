package webserver;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

    private final int initSizeOfThreadPool;
    private final int sizeOfThreadPool;
    private final int keepAliveTime;
    private final int workQueueCapacity;
    private final ThreadPoolExecutor threadPoolExecutor;

    public ThreadPool(ThreadPoolServiceConfiguration configuration) {
        this.initSizeOfThreadPool = configuration.getInitSizeOfThreadPool();
        this.sizeOfThreadPool = configuration.getSizeOfThreadPool();
        this.keepAliveTime = configuration.getKeepAliveTimeSecond();
        this.workQueueCapacity = configuration.getWorkQueueCapacity();
        this.threadPoolExecutor = new ThreadPoolExecutor(
                initSizeOfThreadPool,
                sizeOfThreadPool, keepAliveTime, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(workQueueCapacity)
        );
    }

    public void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }
}
