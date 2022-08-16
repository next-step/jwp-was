package webserver.request;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import webserver.ExecutorsTest;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("HTTP 요청 테스트")
public class HttpRequestTest {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorsTest.class);

    private String testDirectory = "./src/test/resources/";

    @DisplayName("GET Http Request 생성")
    @Test
    void request_GET() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest request = HttpRequest.from(in);
        assertAll(
                () -> assertThat(request.getHttpMethod()).isEqualTo("GET"),
                () -> assertThat(request.getPath()).isEqualTo("/user/create"),
                () -> assertThat(request.getRequestHeader("Connection")).isEqualTo("keep-alive"),
                () -> assertThat(request.getParameter("userId")).isEqualTo("javajigi")
        );
    }

    @DisplayName("Http Request의 header와 requestLine은 공백이어선 안된다.")
    @Test
    void http_request_empty() throws FileNotFoundException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET_FAIL.txt"));
        assertThatThrownBy(
                () -> HttpRequest.from(in))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Content-Length 확인")
    @Test
    void check_content_length() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        HttpRequest request = HttpRequest.from(in);
        assertThat(request.getContentLength()).isEqualTo(59);
    }

    @DisplayName("POST Http Request 생성")
    @Test
    void request_POST() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        HttpRequest request = HttpRequest.from(in);
        assertAll(
                () -> assertThat(request.getHttpMethod()).isEqualTo("POST"),
                () -> assertThat(request.getPath()).isEqualTo("/user/create"),
                () -> assertThat(request.getRequestHeader("Connection")).isEqualTo("keep-alive"),
                () -> assertThat(request.getBody("userId").get()).isEqualTo("javajigi")
        );
    }

    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("설정된 쓰레드풀 보다 많은 파일 요청에도 정상 동작")
    @Test
    void request_index_html_at_the_same_time() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        RestTemplate restTemplate = new RestTemplate();
        AtomicInteger counter = new AtomicInteger(0);
        StopWatch sw = new StopWatch();
        sw.start();

        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                int index = counter.addAndGet(1);
                restTemplate.getForEntity("http://localhost:8080", String.class);
                logger.info("Thread '{}'", index);
            });
        }
        sw.stop();
        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.MILLISECONDS);
        logger.info("Total Elapsed: '{}'", sw.getTotalTimeMillis());
    }
}
