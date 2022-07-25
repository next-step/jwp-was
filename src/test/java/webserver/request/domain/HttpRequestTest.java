package webserver.request.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import webserver.exception.NotFoundRequestLineException;
import webserver.request.domain.request.HttpRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HttpRequestTest {

    @Test
    @DisplayName("HttpRequest 안에 RequestLine이 null 이면 예외를 던진다.")
    void notFoundRequestLineExceptionTest() {
        HttpRequest httpRequest = new HttpRequest();

        assertThrows(NotFoundRequestLineException.class,
                () -> {
            httpRequest.getPath();
        });
    }

    @Test
    @DisplayName("HttpRequest 객체에서 path 값 가져오기 테스트")
    void requestPathTest() {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.addProperty("GET /index.html HTTP/1.1");
        httpRequest.addProperty("Host: localhost:8080");
        httpRequest.addProperty("Connection: keep-alive");
        httpRequest.addProperty("Accept: */*");

        Assertions.assertThat(httpRequest.getPath()).isEqualTo("/index.html");
    }
}
