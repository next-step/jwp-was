package request;

import static org.assertj.core.api.Assertions.assertThat;

import constant.HttpMethod;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpRequestTest {

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
