package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ThreadPoolTest {

    @DisplayName("큐 문제로 예외가 발생하는지 검증")
    @Test
    void catch_rejected_exception() {
        final ThreadPoolServiceConfiguration configuration =
                new ThreadPoolServiceConfiguration(5, 5, 1, 1);
        final ThreadPool threadPool = new ThreadPool(configuration);
        assertThatThrownBy(() -> {
            for (int i = 0; i < 1000; i++) {
                final Runnable dummyRequest = () -> {};
                threadPool.execute(dummyRequest);
            }
        }).isInstanceOf(TaskRejectedException.class);
    }
}