package webserver;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPool {
    public static final int CORE_POOL_SIZE = 250;
    public static final int MAX_POOL_SIZE = 250;
    public static final int MAX_WORK_QUEUE_SIZE = 100;
    public static ThreadPoolExecutor tpe = new ScheduledThreadPoolExecutor(CORE_POOL_SIZE);
    static {
        tpe.setMaximumPoolSize(MAX_POOL_SIZE);
    }

    public static void execute(Runnable runnable) {
        if (MAX_WORK_QUEUE_SIZE <= tpe.getQueue().size()) {
            return;
        }
        tpe.execute(runnable);
    }

}
