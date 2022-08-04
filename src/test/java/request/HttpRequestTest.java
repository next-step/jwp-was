package request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import constant.HttpMethod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpRequestTest {

    private static final String testDirectory = "./src/test/resources/http/";

    @Test
    @DisplayName("GET 피일을 읽어 값을 확인합니다.")
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest request = HttpRequest.parse(in);

        assertEquals(HttpMethod.GET, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

    @Test
    @DisplayName("POST 파일을 읽어 값을 확인합니다.")
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        HttpRequest request = HttpRequest.parse(in);

        assertEquals(HttpMethod.POST, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

    @Test
    public void request_POST2() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST2.txt"));
        HttpRequest request = HttpRequest.parse(in);

        assertEquals(HttpMethod.POST, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("1", request.getParameter("id"));
        assertEquals("javajigi", request.getParameter("userId"));
    }



    @Test
    @DisplayName("파라미터 값을 반환합니다.")
    void getParamterTest() {
        List<String> lines = List.of("GET /index.html?name=1234 HTTP/1.1", "Host: localhost:8080", "Cookie: logined=true");

        HttpRequest httpRequest = HttpRequest.from(lines);

        assertThat(httpRequest.getParameter("name")).isEqualTo("1234");
    }

    @Test
    @DisplayName("header의 값을 반환합니다.")
    void getHeaderTest() {
        List<String> lines = List.of("GET /index.html?name=1234 HTTP/1.1", "Host: localhost:8080", "Cookie: logined=true");

        HttpRequest httpRequest = HttpRequest.from(lines);

        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHeader("Cookie")).isEqualTo("logined=true");
    }

    @Test
    @DisplayName("body값을 추가합니다.")
    void addBodyTest() {
        List<String> lines = List.of("GET /index.html?name=1234 HTTP/1.1", "Host: localhost:8080", "Cookie: logined=true");
        HttpRequest httpRequest = HttpRequest.from(lines);

        String body = "userId=java&password=1234";
        httpRequest.addBody(body);

        Map<String, String> map = new HashMap<>();
        map.put("userId", "java");
        map.put("password", "1234");
        RequestBody requestBody = new RequestBody(map);

        assertThat(httpRequest.getBody()).isEqualTo(requestBody);
    }

    @Test
    @DisplayName("파라미터 값을 반환합니다.")
    void getParameterTest() {
        List<String> lines = List.of("GET /index.html?name=1234 HTTP/1.1", "Host: localhost:8080", "Cookie: logined=true");
        HttpRequest httpRequest = HttpRequest.from(lines);

        assertThat(httpRequest.getParameter("name")).isEqualTo("1234");
    }

    @Test
    @DisplayName("path 값을 반환합니다.")
    void getPath() {
        List<String> lines = List.of("GET /index.html?name=1234 HTTP/1.1", "Host: localhost:8080", "Cookie: logined=true");
        HttpRequest httpRequest = HttpRequest.from(lines);

        assertThat(httpRequest.getPath()).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("Cookie 값만 반환합니다.")
    void getCookieTest() {
        List<String> lines = List.of("GET /index.html?name=1234 HTTP/1.1", "Host: localhost:8080", "Cookie: logined=true");
        HttpRequest httpRequest = HttpRequest.from(lines);

        Cookie cookie = httpRequest.getCoookie();

        assertThat(cookie.getLogined()).isEqualTo("true");
    }

}
