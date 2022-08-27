package http.httprequest;


import http.HttpSession;
import http.SessionAttribute;
import http.SessionStorage;
import http.httprequest.requestbody.RequestBody;
import http.httprequest.requestheader.RequestHeader;
import http.httprequest.requestline.RequestLine;
import http.httpresponse.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpRequestTest {

    @Test
    @DisplayName("정상적으로 잘 생성되는지 확인")
    void create() throws IOException {
        StringBuilder buffer = new StringBuilder();

        for (String current : Arrays.asList(
                "GET /index.html HTTP/1.1\n",
                "Host: localhost:8080\n",
                "Connection: keep-alive\n",
                "Cookie: logined=false\n"
        )) {
            buffer.append(current);
        }

        BufferedReader br = new BufferedReader(new StringReader(buffer.toString()));

        HttpRequest httpRequest = HttpRequest.from(br);

        assertAll(
                () -> assertThat(httpRequest.getRequestLine()).isEqualTo(RequestLine.from("GET /index.html HTTP/1.1")),
                () -> assertThat(httpRequest.getCookieValue("logined")).isEqualTo(Optional.of("false"))
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

    @Test
    @DisplayName("정상적으로 쿠키값을 가져오는지 확인")
    void getCookieValue() {
        HttpRequest httpRequest = HttpRequest.from(
                Arrays.asList(
                        "GET /index.html HTTP/1.1",
                        "Host: localhost:8080",
                        "Connection: keep-alive",
                        "Cookie: logined=false"
                )
        );
        assertThat(httpRequest.getCookieValue("logined")).isEqualTo(Optional.of("false"));
    }

    @Test
    @DisplayName("정상적으로 세션값을 가져오는지 확인")
    void getSession() {
        HttpRequest httpRequest = HttpRequest.from(
                Arrays.asList(
                        "GET /index.html HTTP/1.1",
                        "Host: localhost:8080",
                        "Connection: keep-alive",
                        "Cookie: JSESSIONID=1234"
                )
        );
        SessionStorage.getInstance().add(new HttpSession("1234", new SessionAttribute(Map.of("test", "test"))));

        assertThat(httpRequest.getSession()).isEqualTo(new HttpSession("1234", new SessionAttribute(Map.of("test", "test"))));
    }
}