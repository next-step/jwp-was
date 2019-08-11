package webserver.http.request;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.common.exception.HttpMethodNotSupportedException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author : yusik
 * @date : 2019-08-10
 */
@DisplayName("HttpRequest")
public class HttpRequestTest {

    private static String requestHeader;
    private static String noMethodHeader;

    @BeforeAll
    static void init() {
        requestHeader = new TestRequest.Builder("GET", "/user/login?userName=123&password=123")
                .addHeader("Host", "localhost:8080")
                .addHeader("Connection", "keep-alive")
                .addHeader("Cache-Control", "max-age=0")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("Cookie", "login=true")
                .buildString();

        noMethodHeader = new TestRequest.Builder("TEST", "/user/login?userName=123&password=123")
                .buildString();
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
    void notSupportedMethodTest() {
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
        assertEquals("true", request.getCookie("login"));
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

    @DisplayName("POST 쿼리스트링 파싱")
    @Test
    public void request_POST2() throws Exception {

        String requestHeader = new TestRequest.Builder("POST", "/user/create?id=1")
                .addHeader("Host", "localhost:8080")
                .addHeader("Connection", "keep-alive")
                .addHeader("Content-Length", "46")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "*/*")
                .addBody("userId", "usik")
                .addBody("password", "1234")
                .addBody("name", "kohyusik")
                .buildString();


        InputStream in = new ByteArrayInputStream(requestHeader.getBytes());
        HttpRequest request = new HttpRequest(in);

        assertEquals(HttpMethod.POST, request.getHttpMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("1", request.getParameter("id"));
        assertEquals("usik", request.getParameter("userId"));
    }
}
