package webserver;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class ServerExecutorServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(ServerExecutorServiceTest.class);

    @Test
    void test() throws Exception {
        // given
        final ServerExecutorService serverExecutorService = new ServerExecutorService(10, 5, 0);

        final AtomicInteger acceptedJobCounter = new AtomicInteger();
        final AtomicInteger rejectedJobCounter = new AtomicInteger();


        // when
        logger.info("TEST STARTED");

        for (int i = 0; i < 16; i++) {
            try {
                serverExecutorService.execute(() -> {
                    try {
                        Thread.sleep(200);
                        logger.info("JOB FINISHED");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    acceptedJobCounter.incrementAndGet();
                });
            } catch (RejectedExecutionException e) {
                logger.error("ERROR OCCURRED", e);
                rejectedJobCounter.incrementAndGet();
            }
        }

        Thread.sleep(500);

        // then
        assertThat(acceptedJobCounter.get()).isEqualTo(15);
        assertThat(rejectedJobCounter.get()).isEqualTo(1);
    }
}
