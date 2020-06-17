package webserver.threadPool;

import java.util.concurrent.*;

public class ThreadPoolExecutorFactory {

    public static Executor create(int corePoolSize, int maximumPoolSize, long keepAliveTime, int maximumQueueSize) {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(maximumQueueSize);
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.DAYS, workQueue);
    }

}
