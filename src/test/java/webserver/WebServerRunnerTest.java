package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import utils.StringUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.WebServerRunner.DEFAULT_POOL_SIZE;

class WebServerRunnerTest {
    private static final Logger log = LoggerFactory.getLogger(WebServerRunnerTest.class);
    private static final int TEST_PORT = 8080;

    public static void main(String[] args) throws Exception {
        int requestThreadCount = DEFAULT_POOL_SIZE + 5;
        ExecutorService executorService = Executors.newFixedThreadPool(requestThreadCount);

        StopWatch sw = new StopWatch();
        sw.start();

        IntStream.range(0, requestThreadCount).forEach(n ->
            executorService.execute(() -> requestTest("http://localhost:" + TEST_PORT + "/index.html"))
        );

        sw.stop();

        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.SECONDS);
        log.info("Total Elaspsed: {}", sw.getTotalTimeSeconds());
    }

    private static void requestTest(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        log.debug("response: {}", StringUtils.toPrettyJson(response));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}