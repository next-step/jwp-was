package webserver;

import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {
    public static final int CORE_POOL_SIZE = 250;
    public static final int MAXIMUM_POOL_SIZE = 250;
    public static final long KEEP_ALIVE_TIME = 10L;
    public static final int WORK_QUEUE_SIZE = 100;

    static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
        CORE_POOL_SIZE,
        MAXIMUM_POOL_SIZE,
        KEEP_ALIVE_TIME,
        TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(WORK_QUEUE_SIZE)
    );

    public static void execute(Socket connection) {
        threadPoolExecutor.execute(() -> {
            Thread thread = new Thread(new RequestHandler(connection));
            thread.start();
        });
    }
}
