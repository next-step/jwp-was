package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author KingCjy
 */
public class WebServerTest {

    private static final Logger logger = LoggerFactory.getLogger(WebServerTest.class);
    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void threadPoolTest() throws InterruptedException {
        final int port = 8081;
        Thread thread = new Thread(() -> {
            try {
                WebServer.main(new String[]{String.valueOf(port)});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();

        execute(port, 20);
        logger.info("finish");
    }

    @Test
    @DisplayName("RejectedExecutionException 발생 이벤트")
    public void threadRejectedExecutionExceptionTest() {
        assertThatThrownBy(() -> {
            final int port = 8082;
            new Thread(() -> {
                try {
                    execute(port, 20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

//            port, coreSize, maxSize, queueSize
            WebServer.main(new String[]{String.valueOf(port), "1", "1", "1"});
            logger.info("finish");
        }).isInstanceOf(RejectedExecutionException.class);
    }

    private void execute(int port, int size) throws InterruptedException {
        ExecutorService executorService =  Executors.newFixedThreadPool(20);

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            executorService.execute(new Thread(() -> {
                try {
                    Thread.sleep(5000);
                    sendRequest(port, finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
        }

        executorService.shutdown();
        boolean isTerminated = executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
    }

    private ResponseEntity<String> sendRequest(int port, int requestId) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:"  + port + "/index.html", String.class);
            logger.info("requestId : {}  Status Code: {}", requestId, response.getStatusCode());
            return response;
        } catch (ResourceAccessException e) {}
        return null;
    }
}
