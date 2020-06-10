package webserver;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;

/**
 * @author KingCjy
 */
public class WebServerTest {

    private static final Logger logger = LoggerFactory.getLogger(WebServerTest.class);
    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void threadPoolTest() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                WebServer.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();

        ExecutorService executorService =  Executors.newFixedThreadPool(20);

        for (int i = 0; i < 20; i++) {
            executorService.execute(() -> sendRequest(i));
        }

        executorService.shutdown();
        boolean isTerminated = executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        logger.info("finish");
    }

    private ResponseEntity<String> sendRequest(int requestId) {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/index.html", String.class);
        logger.info("requestId : {}  Status Code: {}", requestId, response.getStatusCode());
        return response;
    }
}
