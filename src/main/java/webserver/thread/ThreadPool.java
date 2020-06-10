package webserver.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 250;
    private static final int KEEP_ALIVE_TIME = 5;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private static final int QUEUE_CAPACITY = 100;
    private static final LinkedBlockingQueue QUEUE = new LinkedBlockingQueue(QUEUE_CAPACITY);

    public static ThreadPoolExecutor execute() {
        return new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TIME_UNIT, QUEUE);
    }
}
