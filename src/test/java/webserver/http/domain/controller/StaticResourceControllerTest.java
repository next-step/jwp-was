package webserver.http.domain.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.FileIoUtils;
import webserver.http.domain.Cookies;
import webserver.http.domain.Headers;
import webserver.http.domain.Protocol;
import webserver.http.domain.request.Method;
import webserver.http.domain.request.Request;
import webserver.http.domain.request.RequestLine;
import webserver.http.domain.request.URI;
import webserver.http.domain.response.Response;
import webserver.http.domain.response.Status;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static webserver.http.domain.request.Method.GET;
import static webserver.http.domain.request.Method.POST;

class StaticResourceControllerTest {

    StaticResourceController staticResourceController = new StaticResourceController();

    @DisplayName("GET 요청이고 ./static 경로 아래 존재하는 파일의 요청이면 true 반환")
    @ParameterizedTest
    @MethodSource("provideForRequires")
    void requires(Method method, String path, boolean expected) {
        RequestLine requestLine = new RequestLine(method, new URI(path), Protocol.http1Point1());
        Request request = new Request(requestLine, new Headers(new LinkedHashMap<>()));

        boolean actual = staticResourceController.requires(request);

        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> provideForRequires() {
        return Stream.of(
                arguments(GET, "/index.html", true),
                arguments(GET, "/nono.html", false),
                arguments(POST, "/index.html", false)
        );
    }

    @DisplayName("정적 index.html 파일을 읽어서  Http 200 응답을 만든다.")
    @Test
    void handle() {
        RequestLine requestLine = new RequestLine(GET, new URI("/index.html"), Protocol.http1Point1());
        Request request = new Request(requestLine, new Headers(new LinkedHashMap<>()));

        Response actual = staticResourceController.handle(request);

        byte[] body = FileIoUtils.loadFileFromClasspath("./static/index.html");

        assertThat(actual).usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(new Response(
                        Status.ok(),
                        new Headers(Map.of(
                                "Content-Type", "text/html",
                                "Content-Length", String.valueOf(body.length)
                        )),
                        new Cookies(Map.of()),
                        body
                ));
    }
}