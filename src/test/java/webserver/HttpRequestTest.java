package webserver;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class HttpRequestTest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestTest.class);

    private static final String RESOURCE_URL = "https://127.0.0.1:8080/index.html";
    private static final int THREAD_COUNT_OF_SERVER = 1;
    private static final int THREAD_COUNT_OF_CLIENT = 900;
    private static final ExecutorService SERVER
            = Executors.newFixedThreadPool(THREAD_COUNT_OF_SERVER);
    private static final ExecutorService CLIENT
            = Executors.newFixedThreadPool(THREAD_COUNT_OF_CLIENT);

    @BeforeAll
    static void setUp() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                try {
                    e.getStackTrace();
                    throw new RejectedExecutionException("Too much requests has occurred!");
                } catch (Throwable exception) {
                    throw exception;
                }
            }
        });
    }

    @DisplayName("모든 Thread가 사용중이고, Queue까지 꽉 차서 넘치면, Thread에 발생한 Exception이 로그에 기록된다.")
    @Test
    void requestTest() throws InterruptedException {
        //given
        SERVER.execute(() -> {
            try {
                WebServer.main(new String[0]);
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //when
        IntStream.range(0, THREAD_COUNT_OF_CLIENT).forEach(i -> {
            CLIENT.submit(() -> executeRestTemplate(RESOURCE_URL));
        });

        afterExecution(SERVER, CLIENT);
    }

    private void executeRestTemplate(String resourceUrl) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForEntity(resourceUrl, String.class);
    }

    private void afterExecution(ExecutorService server, ExecutorService client) throws InterruptedException {
        server.shutdown();
        server.awaitTermination(5, TimeUnit.SECONDS);

        client.shutdown();
        client.awaitTermination(5, TimeUnit.SECONDS);
    }
}
