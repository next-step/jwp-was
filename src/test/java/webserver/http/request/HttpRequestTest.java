package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";
    HttpRequest httpRequest = new HttpRequest(new RequestLine("GET /users HTTP/1.1"), new RequestHeader(Map.of("Connection", "keep-alive")), new RequestBody("userId=aaaa"));

    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("Maximum 요청보다 더 많은 요청을 했을 때 정상응답에 대한 테스트")
    @Test
    void testRequestBeyondMaximumPool() {
        RestTemplate restTemplate = new RestTemplate();

        IntStream.range(0, 999)
                .parallel()
                .mapToObj(thread -> restTemplate.getForEntity("http://localhost:8080/index.html", String.class).getStatusCode())
                .forEach(httpStatus -> assertThat(httpStatus).isEqualTo(HttpStatus.OK));
    }

    @Test
    void testIsGet() {
        assertThat(httpRequest.isGet()).isTrue();
    }

    @Test
    void testIsPost() {
        assertThat(httpRequest.isPost()).isFalse();
    }

    @Test
    void testGetHeaders() {
        assertThat(httpRequest.getHeaders()).isEqualTo(new RequestHeader(Map.of("Connection", "keep-alive")));
    }

    @Test
    void testGetParameter() {
        assertThat(httpRequest.getParameter("userId")).isEqualTo(new RequestBody("userId=aaaa").getRequestBodyMap().get("userId"));
    }

    @Test
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest request = new HttpRequest(in);

        assertEquals("GET", request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

    @Test
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        HttpRequest request = new HttpRequest(in);

        assertEquals("POST", request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

    @Test
    public void request_POST2() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST2.txt"));
        HttpRequest request = new HttpRequest(in);

        assertEquals("POST", request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("1", request.getParameter("id"));
        assertEquals("javajigi", request.getParameter("userId"));
    }
}