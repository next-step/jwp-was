package http.controller;

import http.requests.HttpRequest;
import http.responses.HttpResponse;
import http.types.HttpMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class RequestMapperTest {

    private HttpRequest mockRequest;

    @BeforeEach
    void setUp() throws Exception {
        final String testRequest =
                "GET /test-path HTTP/1.1\r\n" +
                        "Host: localhost:8080\r\n" +
                        "Content-Length: 76\r\n" +
                        "Content-Type: application/x-www-form-urlencoded\r\n" +
                        "\r\n" +
                        "userId=hyeyoom&password=1234abcd&name=Chiho+Won&email=neoul_chw%40icloud.com";
        try (final InputStream input = new ByteArrayInputStream(testRequest.getBytes())) {
            mockRequest = new HttpRequest(input);
        }
    }

    @DisplayName("적절한 컨트롤러로 분기하는지 테스트")
    @Test
    void dispatch_request() {
        RequestMapper.addController("/test-path", HttpMethod.GET, new MockController());
        final Controller controller = RequestMapper.dispatch(mockRequest);
        assertThat(controller).isInstanceOf(MockController.class);
    }

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
            final Controller controller = RequestMapper.dispatch(mockRequest);
            assertThat(controller.getClass().getSimpleName()).isEqualTo(clazz);
        }
    }

    private static class MockController implements Controller {
        @Override
        public void service(HttpRequest request, HttpResponse response) {
            // no-op
        }
    }
}