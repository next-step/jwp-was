package http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import http.exception.NotFoundExtensionException;

class HttpRequestTest {

    @Test
    void isStaticFile() {
        var requestLine = new RequestLine("GET /users/index.html HTTP/1.1");
        var headers = new Headers(List.of());
        var httpRequest = new HttpRequest(requestLine, headers, "");

        assertThat(httpRequest.isStaticFile()).isTrue();
    }

    @Test
    void getUrl() {
        var requestLine = new RequestLine("GET /users HTTP/1.1");
        var headers = new Headers(List.of());
        var httpRequest = new HttpRequest(requestLine, headers, "");

        assertThat(httpRequest.getUrl()).isEqualTo("/users");
    }

    @Test
    void getMethod() {
        var requestLine = new RequestLine("GET /users HTTP/1.1");
        var headers = new Headers(List.of());
        var httpRequest = new HttpRequest(requestLine, headers, "");

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
    }

    @Test
    void getCookie() {
        var requestLine = new RequestLine("GET /users HTTP/1.1");
        var headers = new Headers(List.of("Cookie: logined=true"));
        var httpRequest = new HttpRequest(requestLine, headers, "");

        var actual = httpRequest.getCookie("logined")
            .get();

        assertThat(actual).isEqualTo("true");
    }

    @Test
    void getFileExtension() {
        var requestLine = new RequestLine("GET /users/index.html HTTP/1.1");
        var headers = new Headers(List.of());
        var httpRequest = new HttpRequest(requestLine, headers, "");

        var fileExtension = httpRequest.getFileExtension();

        assertThat(fileExtension).isEqualTo("html");
    }

    @DisplayName("확장자를 찾을때, 정적파일이 아닌 경우 예외를 반환한다.")
    @Test
    void getFileExtensionError() {
        var requestLine = new RequestLine("GET /users/index HTTP/1.1");
        var headers = new Headers(List.of());
        var httpRequest = new HttpRequest(requestLine, headers, "");

        assertThatThrownBy(httpRequest::getFileExtension)
            .isInstanceOf(NotFoundExtensionException.class)
            .hasMessageStartingWith("확장자를 찾을 수 없습니다.");
    }

    @Test
    void parse() {
        var request = "GET /index.html HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*";
        var inputStream = new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8));
        var bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        var httpRequest = HttpRequest.parse(bufferedReader);

        assertAll(
            () -> assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET),
            () -> assertThat(httpRequest.getUrl()).isEqualTo("/index.html"),
            () -> assertThat(httpRequest.isStaticFile()).isTrue()
        );
    }
}