package webserver.http.request;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.exception.HttpMethodNotSupportedException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author : yusik
 * @date : 2019-08-10
 */
public class HttpRequestTest {

    private static String requestHeader;
    private static String noMethodHeader;

    @BeforeAll
    public static void init() {
        requestHeader =
                "GET /user/login?userName=123&password=123 HTTP/1.1\r\n" +
                        "Host: localhost:8080\r\n" +
                        "Connection: keep-alive\r\n" +
                        "Cache-Control: max-age=0\r\n" +
                        "Upgrade-Insecure-Requests: 1\r\n" +
                        "Cookie: login=true\r\n" +
                        "\r\n";

        noMethodHeader =
                "TEST /user/login?username=123&password=123 HTTP/1.1\r\n" +
                        "\r\n";
    }

    @DisplayName("HTTP 메서드 파싱")
    @Test
    void methodTest() throws IOException {

        InputStream in = new ByteArrayInputStream(requestHeader.getBytes());
        HttpRequest request = new HttpRequest(in);

        assertTrue(HttpMethod.contains(request.getHttpMethod()));
    }

    @DisplayName("path 파싱")
    @Test
    void requestPathTest() throws IOException {

        InputStream in = new ByteArrayInputStream(requestHeader.getBytes());
        HttpRequest request = new HttpRequest(in);

        assertEquals("/user/login", request.getPath());
    }

    @DisplayName("예외 테스트: 허용되지 않는 method")
    @Test
    void notSupportedMethodTest() throws IOException {
        InputStream in = new ByteArrayInputStream(noMethodHeader.getBytes());
        assertThrows(HttpMethodNotSupportedException.class, () -> new HttpRequest(in));
    }

    @DisplayName("header 파싱")
    @Test
    void requestHeaderTest() throws IOException {
        InputStream in = new ByteArrayInputStream(requestHeader.getBytes());
        HttpRequest request = new HttpRequest(in);
        Map<String, String> headers = request.getHeaders();
        assertEquals("localhost:8080", headers.get("Host"));
        assertEquals("keep-alive", headers.get("Connection"));
    }

    @DisplayName("cookie 파싱")
    @Test
    void cookieTest() throws IOException {
        InputStream in = new ByteArrayInputStream(requestHeader.getBytes());
        HttpRequest request = new HttpRequest(in);
        assertEquals("login=true", request.getCookie());
    }

    @DisplayName("http version 파싱")
    @Test
    void httpVersionTest() throws IOException {
        InputStream in = new ByteArrayInputStream(requestHeader.getBytes());
        HttpRequest request = new HttpRequest(in);
        assertEquals("HTTP/1.1", request.getHttpVersion());
    }

    @DisplayName("파라미터 파싱")
    @Test
    void getParameterTest() throws IOException {
        InputStream in = new ByteArrayInputStream(requestHeader.getBytes());
        HttpRequest request = new HttpRequest(in);
        assertEquals("123", request.getParameter("userName"));
        assertEquals("123", request.getParameter("password"));
    }
}
