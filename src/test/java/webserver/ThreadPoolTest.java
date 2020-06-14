package webserver;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

/**
 * Created by iltaek on 2020/06/14 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class ThreadPoolTest {

    @DisplayName("처리 가능한 요청범위를 초과하는 요청이 올때 예외를 던지는지 테스트")
    @Test
    void exceededRequestTest() {
        assertThatThrownBy(() -> {
            Runnable runnable = () -> {
                try {
                    executeThreadPool(5);
                } catch (Exception ignored) {
                }
            };
            new Thread(runnable).start();

            // WebServer start
            // core, max thread pool size = 1
            // wait queue size = 1
            WebServer.main(new String[]{"8080", "1", "1", "10", "1"});
        }).isInstanceOf(RejectedExecutionException.class);
    }

    private void executeThreadPool(int size) throws Exception {
        StopWatch sw = new StopWatch();
        sw.start();
        ExecutorService es = Executors.newFixedThreadPool(size);
        for (int i = 0; i < 5; i++) {
            es.execute(new Thread(() -> {
                try {
                    int result = sendRequest();
                    System.out.println("Request result: " + result);
                    Thread.sleep(1000);
                } catch (Exception ignored) {
                }
            }));
        }
        sw.stop();

        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);
    }

    private int sendRequest() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/index.html", String.class);
        return response.getStatusCodeValue();
    }
}
