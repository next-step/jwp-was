package webserver.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import http.request.HttpRequest;
import http.request.session.MemorySessionStore;
import http.response.HttpResponse;
import webserver.ControllerIdentity;
import webserver.controller.exception.ControllerNotFoundException;

class RouterTest {

    private Router router;

    @BeforeEach
    void setUp() {
        Map<ControllerIdentity, Controller> value = Map.of(new ControllerIdentity("/test"),
            (GetController)request -> HttpResponse.ok("테스트응답"));
        router = new Router(value);
    }

    @DisplayName("정적파일을 요청시 라우터와 무관한, 파일이름과 일치하는 파일을 응답한다.")
    @Test
    void handle() {
        var request = "GET /index.html HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*";
        var inputStream = new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8));
        var bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        var httpRequest = HttpRequest.parse(bufferedReader, new MemorySessionStore());

        var response = router.handle(httpRequest);

        assertAll(
            () -> assertThat(response.getHttpStatus().getStatusCode()).isEqualTo(200),
            () -> assertThat(response.getHeaders().get("Content-Type")).isEqualTo("text/html")
        );
    }

    @DisplayName("라우터에 등록되지않는 요청시 예외가 발생한다")
    @Test
    void name() {
        var request = "GET /user/list HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*";
        var inputStream = new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8));
        var bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        var httpRequest = HttpRequest.parse(bufferedReader, new MemorySessionStore());

        assertThatThrownBy(
            () -> router.handle(httpRequest))
            .isInstanceOf(ControllerNotFoundException.class)
            .hasMessageStartingWith("컨트롤러를 찾을 수 없습니다.");
    }

    @DisplayName("라우터에 해당하는 컨트롤러를 찾을 수 있다.")
    @Test
    void name2() {
        var request = "GET /test HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*";
        var inputStream = new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8));
        var bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        var httpRequest = HttpRequest.parse(bufferedReader, new MemorySessionStore());

        var response = router.handle(httpRequest);

        assertAll(
            () -> assertThat(response.getHttpStatus().getStatusCode()).isEqualTo(200),
            () -> assertThat(response.getBody()).isEqualTo("테스트응답")
        );
    }
}