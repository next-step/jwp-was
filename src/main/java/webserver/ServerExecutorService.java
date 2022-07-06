package webserver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ServerExecutorService {
    private static final int THREAD_POOL_SIZE = 250;
    private static final int WORK_QUEUE_SIZE = 100;
    private static final long WAIT_TIME_MILLIS = 0;

    private final ExecutorService executorService;

    public ServerExecutorService() {
        this(THREAD_POOL_SIZE, WORK_QUEUE_SIZE, WAIT_TIME_MILLIS);
    }

    public ServerExecutorService(int threadPoolSize, int workQueueSize, long waitTimeMillis) {
        this.executorService =  new ThreadPoolExecutor(
                threadPoolSize,
                threadPoolSize,
                waitTimeMillis,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(workQueueSize)
        );
    }

    public void execute(final Runnable command) {
        this.executorService.execute(command);
    }
}
