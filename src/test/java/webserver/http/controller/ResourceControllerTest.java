package webserver.http.controller;

import org.junit.jupiter.api.Test;
import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResourceControllerTest {

    @Test
    void HTML_템플릿_응답() throws IOException {
        // given
        final ResourceController resourceController = new ResourceController();

        final HttpRequest httpRequest = mock(HttpRequest.class);
        when(httpRequest.getMethod()).thenReturn(HttpMethod.GET);
        when(httpRequest.getPath()).thenReturn("/test.html");

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        // when
        resourceController.service(httpRequest, httpResponse);

        // then
        final String rawHttpResponse = byteArrayOutputStream.toString();
        assertThat(rawHttpResponse)
                .isEqualTo("HTTP/1.1 200 OK \r\n" +
                        "Content-Length: 27\r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n" +
                        "\r\n" +
                        "<html><h1>test</h1></html>\n"
                );
    }

    @Test
    void CSS_응답() throws IOException {
        // given
        final ResourceController resourceController = new ResourceController();

        final HttpRequest httpRequest = mock(HttpRequest.class);
        when(httpRequest.getMethod()).thenReturn(HttpMethod.GET);
        when(httpRequest.getPath()).thenReturn("/css/test.css");

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        // when
        resourceController.service(httpRequest, httpResponse);

        // then
        final String rawHttpResponse = byteArrayOutputStream.toString();
        assertThat(rawHttpResponse)
                .isEqualTo("HTTP/1.1 200 OK \r\n" +
                        "Content-Length: 19\r\n" +
                        "Content-Type: text/css;charset=utf-8\r\n" +
                        "\r\n" +
                        "h1 { color: red; }\n"
                );
    }

    @Test
    void JS_응답() throws IOException {
        // given
        final ResourceController resourceController = new ResourceController();

        final HttpRequest httpRequest = mock(HttpRequest.class);
        when(httpRequest.getMethod()).thenReturn(HttpMethod.GET);
        when(httpRequest.getPath()).thenReturn("/js/test.js");

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        // when
        resourceController.service(httpRequest, httpResponse);

        // then
        final String rawHttpResponse = byteArrayOutputStream.toString();
        assertThat(rawHttpResponse)
                .isEqualTo("HTTP/1.1 200 OK \r\n" +
                        "Content-Length: 21\r\n" +
                        "Content-Type: text/javascript;charset=utf-8\r\n" +
                        "\r\n" +
                        "console.log(\"test\");\n"
                );
    }
}
