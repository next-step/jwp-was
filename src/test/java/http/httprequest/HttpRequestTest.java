package http.httprequest;


import http.httprequest.requestbody.RequestBody;
import http.httprequest.requestheader.RequestHeader;
import http.httprequest.requestline.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpRequestTest {

    @Test
    @DisplayName("정상적으로 잘 생성되는지 확인")
    void create() {
        HttpRequest httpRequest = HttpRequest.from(
                Arrays.asList(
                        "GET /index.html HTTP/1.1",
                        "Host: localhost:8080",
                        "Connection: keep-alive",
                        "Cookie: logined=false"
                )
        );

        assertAll(
                () -> assertThat(httpRequest.getRequestLine()).isEqualTo(RequestLine.from("GET /index.html HTTP/1.1")),
                () -> assertThat(httpRequest.getCookie()).isEqualTo("logined=false")
        );
    }

    @Test
    @DisplayName("정상적으로 body값을 잘 가져오는지 확인")
    void getBodyValue() {
        HttpRequest httpRequest = new HttpRequest(
                RequestLine.from("GET /index.html HTTP/1.1"),
                RequestHeader.from(List.of("Host: localhost:8090")),
                new RequestBody(Map.of("userId", "javajigi", "password", "password", "name", "자바지기", "email", "javajigi@gmail.com"))
        );

        assertThat(httpRequest.getBodyValue("userId")).isEqualTo("javajigi");
    }

    @Test
    @DisplayName("정상적으로 contentLength값을 잘 가져오는지 확인")
    void getContentLength() {
        HttpRequest httpRequest = new HttpRequest(
                RequestLine.from("GET /index.html HTTP/1.1"),
                RequestHeader.from(List.of("Host: localhost:8090", "Content-Length: 75")),
                new RequestBody(Map.of("userId", "javajigi", "password", "password", "name", "자바지기", "email", "javajigi@gmail.com"))
        );

        assertThat(httpRequest.getContentLength()).isEqualTo(75);
    }
}