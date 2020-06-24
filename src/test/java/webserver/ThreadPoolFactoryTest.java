package webserver;

import org.junit.jupiter.api.Test;


import java.util.concurrent.ThreadPoolExecutor;

import static org.assertj.core.api.Assertions.assertThat;

class ThreadPoolFactoryTest {

    @Test
    void test_createThreadPool() {
        // given
        int poolSize = 250;
        int queueSize = 100;
        // when
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) ThreadPoolFactory.newFixedThreadPoolWithQueueSize(poolSize, queueSize);
        // then
        assertThat(executorService.getCorePoolSize()).isEqualTo(poolSize);
        assertThat(executorService.getQueue().remainingCapacity()).isEqualTo(queueSize);
    }
}