package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import utils.FileIoUtils;
import webserver.domain.HttpRequest;
import webserver.domain.Path;
import webserver.domain.Protocol;
import webserver.domain.RequestBody;
import webserver.domain.RequestLine;
import webserver.domain.Version;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    public static final String USERS_PATH = "/users";
    public static final String HTTP_PROTOCOL = "HTTP";
    public static final Version TEST_VERSION = Version.ONE_DOT_ONE;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void request_resttemplate() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("HTTP GET 요청에 대한 파싱 결과를 확인할 수 있다.")
    @Test
    void getRequestLine() throws IOException {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/users",String.class);

        HttpRequest httpRequest = toHttpRequest(response);
        RequestLine requestLine = httpRequest.getRequestLine();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo(new Path(USERS_PATH));
        assertThat(requestLine.getProtocol()).isEqualTo(new Protocol(HTTP_PROTOCOL, TEST_VERSION));
    }

    @DisplayName("HTTP POST 요청에 대한 파싱 결과를 확인할 수 있다.")
    @Test
    void postRequestLine() throws IOException {
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/users", null, String.class);

        HttpRequest httpRequest = toHttpRequest(response);
        RequestLine requestLine = Objects.requireNonNull(httpRequest).getRequestLine();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getPath()).isEqualTo(new Path(USERS_PATH));
        assertThat(requestLine.getProtocol()).isEqualTo(new Protocol(HTTP_PROTOCOL, TEST_VERSION));

    }

    @DisplayName("Query String 데이터를 파싱한 뒤 그 결과를 반환한다.")
    @Test
    void getQueryString() throws IOException {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/users?userId=catsbi&password=password&name=hansol",
                String.class);

        HttpRequest httpRequest = toHttpRequest(response);
        RequestBody requestBody = Objects.requireNonNull(httpRequest).getRequestBody();
        RequestLine requestLine = Objects.requireNonNull(httpRequest).getRequestLine();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo(new Path(USERS_PATH));
        assertThat(requestLine.getProtocol()).isEqualTo(new Protocol(HTTP_PROTOCOL, TEST_VERSION));
        assertThat(requestBody.getAttribute("userId")).isEqualTo("catsbi");
        assertThat(requestBody.getAttribute("password")).isEqualTo("password");
        assertThat(requestBody.getAttribute("name")).isEqualTo("hansol");
    }

    @DisplayName("${host}/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.")
    @Test
    void requestIndexHtml() throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        String expectedBody = new String(body);

        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/index.html", String.class);

        assertThat(response.getBody()).isEqualTo(expectedBody);
    }


    @DisplayName("서버가 수용하는 최대 스레드풀 이하로 요청을 보내본다. ")
    @Test
    void requestUnderMaxThreadSize() throws ExecutionException, InterruptedException {
        int maxRequestCount = 10;
        CountDownLatch countDownLatch = new CountDownLatch(maxRequestCount);
        ExecutorService executorService = Executors.newFixedThreadPool(maxRequestCount);
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        List<CompletableFuture<Void>> futures = IntStream.range(0, maxRequestCount)
                .mapToObj(idx -> CompletableFuture.runAsync(() -> {
                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080", String.class);
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                    countDownLatch.countDown();

                }, executorService))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).get();
        boolean await = countDownLatch.await(1000, TimeUnit.MILLISECONDS);
        stopWatch.stop();

        System.out.println("stopWatch.getTotalTimeSeconds() = " + stopWatch.getTotalTimeSeconds());
        assertThat(countDownLatch.getCount()).isZero();
        assertThat(await).isTrue();
        assertThat(stopWatch.getTotalTimeSeconds()).isLessThanOrEqualTo(1);
    }

    @DisplayName("서버가 수용하는 최대 스레드풀 이상으로 요청을 보내본다. ")
    @Test
    void requestOverMaxThreadSize() throws ExecutionException, InterruptedException {
        int maxRequestCount = 50;
        CountDownLatch countDownLatch = new CountDownLatch(maxRequestCount);
        ExecutorService executorService = Executors.newFixedThreadPool(maxRequestCount);
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        List<CompletableFuture<Void>> futures = IntStream.range(0, maxRequestCount)
                .mapToObj(idx -> CompletableFuture.runAsync(() -> {
                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080", String.class);
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                    countDownLatch.countDown();

                }, executorService))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).get();
        boolean await = countDownLatch.await(1000, TimeUnit.MILLISECONDS);
        stopWatch.stop();

        System.out.println("stopWatch.getTotalTimeSeconds() = " + stopWatch.getTotalTimeSeconds());
        assertThat(countDownLatch.getCount()).isZero();
        assertThat(await).isTrue();
        assertThat(stopWatch.getTotalTimeSeconds()).isLessThanOrEqualTo(1);
    }

    private HttpRequest toHttpRequest(ResponseEntity<String> response) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream(Objects.requireNonNull(response.getBody()).trim().getBytes(StandardCharsets.UTF_8))));

        return HttpRequest.newInstance(br);
    }
}
