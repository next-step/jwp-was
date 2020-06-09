package http.controller;

import http.requests.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.dispatcher.RequestDispatcher;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class RequestDispatcherTest {

    @DisplayName("스태틱, 템플릿, 404 컨트롤러 테스트")
    @ParameterizedTest
    @CsvSource(value = {
            "/index.html,TemplateController",
            "/css/abc.css,StaticController",
            "/js/daf.js,StaticController",
            "/no_such_thing,NotFoundController",
    })
    void dispatch_built_in_controllers(String path, String clazz) throws Exception {
        final String testRequest =
                "GET %s HTTP/1.1\r\n" +
                        "Host: localhost:8080\r\n" +
                        "Content-Type: application/x-www-form-urlencoded\r\n" +
                        "\r\n";
        final String request = String.format(testRequest, path);
        try (final InputStream input = new ByteArrayInputStream(request.getBytes())) {
            final HttpRequest mockRequest = new HttpRequest(input);
            final Controller controller = RequestDispatcher.dispatch(mockRequest);
            assertThat(controller.getClass().getSimpleName()).isEqualTo(clazz);
        }
    }
}