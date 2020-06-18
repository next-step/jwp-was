package webserver.threadPool;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ThreadPoolExecutorFactoryTest {
    static final int DEFAULT_CORE_POOL_SIZE = 10;
    static final int DEFAULT_THREAD_POOL_SIZE = 10;
    static final long DEFAULT_KEEP_ALIVE_TIME = 10L;
    static final int DEFAULT_MAXIMUM_QUEUE_SIZE = 10;

    @Test
    @DisplayName("ThreadPool이 정상적으로 생성된다")
    void threadPool() {
        final Executor result = ThreadPoolExecutorFactory.create(DEFAULT_CORE_POOL_SIZE, DEFAULT_THREAD_POOL_SIZE, DEFAULT_KEEP_ALIVE_TIME, DEFAULT_MAXIMUM_QUEUE_SIZE);

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("maximumPoolSize가 코어 풀 크기보다 작을 경우 에러를 던진다")
    void maxPoolSizeCorePoolSize() {
        final int corePoolSize = 5;
        final int maximumPoolSize = 4;

        final Throwable thrown = catchThrowable(() -> ThreadPoolExecutorFactory.create(corePoolSize, maximumPoolSize, DEFAULT_KEEP_ALIVE_TIME, DEFAULT_MAXIMUM_QUEUE_SIZE));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("corePoolSize가 0보다 작을 경우 에러를 던진다")
    void illegalCorePoolSize() {
        final int corePoolSize = -1;

        final Throwable thrown = catchThrowable(() -> ThreadPoolExecutorFactory.create(corePoolSize, DEFAULT_THREAD_POOL_SIZE, DEFAULT_KEEP_ALIVE_TIME, DEFAULT_MAXIMUM_QUEUE_SIZE));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("keepAliveTime  0보다 작을 경우 에러를 던진다")
    void illegalKeepAliveTime() {
        final long keepAliveTime = -1;

        final Throwable thrown = catchThrowable(() -> ThreadPoolExecutorFactory.create(DEFAULT_CORE_POOL_SIZE, DEFAULT_THREAD_POOL_SIZE, keepAliveTime, DEFAULT_MAXIMUM_QUEUE_SIZE));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("maximumQueue 가 0보다 작을 경우 에러를 던진다")
    void illegalMaximumQueue() {
        final int maximumQueue = -1;

        final Throwable thrown = catchThrowable(() -> ThreadPoolExecutorFactory.create(DEFAULT_CORE_POOL_SIZE, DEFAULT_THREAD_POOL_SIZE, DEFAULT_KEEP_ALIVE_TIME, maximumQueue));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class);
    }

}