package webserver;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class ExecutorsTest {

    @Test
    void performance_test() throws InterruptedException, BrokenBarrierException, ExecutionException {
        final int poolSize = 10;
        WebServer webServer = rueWebServer(poolSize);

        List<Future<HttpStatus>> status = call(100);

        for (Future<HttpStatus> httpStatus : status){
            assertThat(httpStatus.get()).isEqualTo(HttpStatus.OK);
        }
        webServer.stop();
    }

    private List<Future<HttpStatus>> call(final int requestCount) throws InterruptedException, BrokenBarrierException {
        ExecutorService executor = Executors.newFixedThreadPool(requestCount);

        CyclicBarrier barrier = new CyclicBarrier(requestCount + 1);

        List<Future<HttpStatus>> status = IntStream.range(0, requestCount).mapToObj(value -> executor.submit(() -> {
            RestTemplate restTemplate = new RestTemplate();
            try {
                barrier.await();
            } catch (BrokenBarrierException | InterruptedException e) {
                e.printStackTrace();
            }
            try {
                ResponseEntity<String> response = restTemplate
                    .getForEntity("http://localhost:9000", String.class);
                return response.getStatusCode();
            } catch (RestClientException e) {
               return HttpStatus.BAD_GATEWAY;
            }
        })).collect(Collectors.toList());
        barrier.await();

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        return status;
    }

    private WebServer rueWebServer(int poolSize) {
        WebServer webServer = new WebServer(9000,
            new ThreadPoolExecutor(poolSize, poolSize, 1, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100))
        );

        Thread thread = new Thread(() -> {
            try {
                webServer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        while (!webServer.isRunning()) {
        }

        return webServer;
    }
}

