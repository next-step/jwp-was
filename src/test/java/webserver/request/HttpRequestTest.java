package webserver.request;

import org.junit.jupiter.api.Test;
import webserver.HttpMethod;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    @Test
    void HTTP_요청에_대한_InputStream을_HttpRequest_객체로_변환한다_POST_URL_ENCODED() throws IOException {
        // given
        final String rawHttpRequest = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 93\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        try (final InputStream in = new ByteArrayInputStream(rawHttpRequest.getBytes(StandardCharsets.UTF_8))) {
            // when
            final HttpRequest httpRequest = new HttpRequest(in);

            // then - Request Line
            final RequestLine requestLine = httpRequest.getRequestLine();
            assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
            assertThat(requestLine.getPath()).isEqualTo("/user/create");
            assertThat(requestLine.getQueryString()).isEqualTo("");
            assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
            assertThat(requestLine.getVersion()).isEqualTo("1.1");

            // then - Request Headers
            final RequestHeaders headers = httpRequest.convertToRequestHeaders();
            assertThat(headers.getHost()).isEqualTo("localhost:8080");
            assertThat(headers.getConnection()).isEqualTo("keep-alive");
            assertThat(headers.getContentLength()).isEqualTo(93);
            assertThat(headers.getContentType()).isEqualTo("application/x-www-form-urlencoded");
            assertThat(headers.getAccept()).isEqualTo("*/*");

            // then - Request Body
            final RequestBody requestBody = httpRequest.getBody();
            assertThat(requestBody.get("userId")).isEqualTo("javajigi");
            assertThat(requestBody.get("password")).isEqualTo("password");
            assertThat(requestBody.get("name")).isEqualTo("박재성");
            assertThat(requestBody.get("email")).isEqualTo("javajigi@slipp.net");
        }
    }
}
