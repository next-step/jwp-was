package webserver;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by iltaek on 2020/06/14 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class ConfiguredThreadPoolExecutorWrapper {

    private ConfiguredThreadPoolExecutorWrapper() {
    }

    public static Executor newConfiguredThreadPool(ThreadPoolConfiguration config) {
        return new ThreadPoolExecutor(config.getCorePoolSize(), config.getMaxPoolSize(), config.getKeepAliveTime(), TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(config.getBlockQueueCapacity()));
    }
}
