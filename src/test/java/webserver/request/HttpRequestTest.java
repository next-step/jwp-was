package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static webserver.request.RequestLineTest.TEST_GET_REQUEST_LINE;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import webserver.enums.HttpMethod;
import webserver.enums.Protocol;

class HttpRequestTest {

    private HttpRequest httpRequest;
    private String testDirectory = "./src/test/resources/";

    @BeforeEach
    void setUp() {
        httpRequest = new HttpRequest(TEST_GET_REQUEST_LINE);
    }

    @Test
    void request_GET() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_GET.txt");
        HttpRequest request = HttpRequest.of(in);

        assertEquals(HttpMethod.GET, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

    @Test
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_POST.txt");
        HttpRequest request = HttpRequest.of(in);

        assertEquals(HttpMethod.POST, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

    @DisplayName("헤더에 키-밸류 형태로 값이 정상 저장된다.")
    @Test
    void headerTest() {
        httpRequest.addHeader("Host", "localhost:8080");
        httpRequest.addHeader("Connection", "keep-alive");
        httpRequest.addHeader("Content-Length", "95");
        httpRequest.addHeader("Cache-Control", "max-age=0");
        httpRequest.addHeader("Origin", "http://localhost:8080");
        httpRequest.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpRequest.addHeader("Accept-Encoding", "gzip, deflate, br");
        httpRequest.addHeader("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");

        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getHeader("Content-Length")).isEqualTo("95");
        assertThat(httpRequest.getHeader("Cache-Control")).isEqualTo("max-age=0");
        assertThat(httpRequest.getHeader("Origin")).isEqualTo("http://localhost:8080");
        assertThat(httpRequest.getHeader("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
        assertThat(httpRequest.getHeader("Accept-Encoding")).isEqualTo("gzip, deflate, br");
        assertThat(httpRequest.getHeader("Accept-Language")).isEqualTo("ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
    }

    @Test
    void createTest() {
        HttpRequest httpRequestTest = new HttpRequest(TEST_GET_REQUEST_LINE);

        assertThat(httpRequestTest.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequestTest.getPath()).isEqualTo("/users");
        assertThat(httpRequestTest.getProtocol()).isEqualTo(Protocol.HTTP_1_1);
    }

    @Disabled("로컬 8080 요청/응답 테스트 (서버 동작 후 테스팅 가능)")
    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
