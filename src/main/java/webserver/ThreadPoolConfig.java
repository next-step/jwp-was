package webserver;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolConfig {

    private static final int DEFAULT_THREAD_COUNT = 10;

    private final WebApplicationConfig webApplicationConfig;

    public ThreadPoolConfig(WebApplicationConfig webApplicationConfig) {
        this.webApplicationConfig = webApplicationConfig;
    }

    public ExecutorService create() {
        if (Objects.nonNull(webApplicationConfig.getThreadPoolSize())) {
            return Executors.newFixedThreadPool(webApplicationConfig.getThreadPoolSize());
        }
        return Executors.newFixedThreadPool(DEFAULT_THREAD_COUNT);
    }
}
