package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.session.HttpSessionContext;

class HttpRequestParserTest {

    @DisplayName("HTTP GET 요청 정보를 읽어 HttpRequest 객체를 생성한다")
    @Test
    void parse_http_request_with_get() throws IOException {
        // given
        String httpRequestMessage = "GET /index.html?userId=admin&email=admin@email.com HTTP/1.1\r\n"
            + "Host: localhost:8080\r\n"
            + "Connection: keep-alive\r\n"
            + "\r\n";

        BufferedReader bufferedReader = getBufferedReader(httpRequestMessage);

        final RequestLine requestLine = RequestLine.of("GET", "/index.html?userId=admin&email=admin@email.com", "HTTP/1.1");
        final RequestHeaders requestHeaders = new RequestHeaders();
        requestHeaders.add("Host: localhost:8080");
        requestHeaders.add("Connection: keep-alive");
        final RequestBody requestBody = RequestBody.EMPTY_REQUEST_BODY;

        // when
        final HttpRequest actual = HttpRequestParser.parse(bufferedReader);

        // then
        assertThat(actual).isEqualTo(new HttpRequest(requestLine, requestHeaders, requestBody));
    }

    @DisplayName("HTTP POST 요청 정보를 읽어 HttpRequest 객체를 생성한다")
    @Test
    void parse_http_request_with_post() throws IOException {
        // given
        String httpRequestMessage = "POST /index.html HTTP/1.1\r\n"
            + "Host: localhost:8080\r\n"
            + "Connection: keep-alive\r\n"
            + "Content-Length: 34\r\n"
            + "\r\n"
            + "userId=admin&email=admin@email.com\n\n";

        BufferedReader bufferedReader = getBufferedReader(httpRequestMessage);

        final RequestLine requestLine = RequestLine.of("POST", "/index.html", "HTTP/1.1");
        final RequestHeaders requestHeaders = new RequestHeaders();
        requestHeaders.add("Host: localhost:8080");
        requestHeaders.add("Connection: keep-alive");
        requestHeaders.add("Content-Length: 34");
        final RequestBody requestBody = new RequestBody("userId=admin&email=admin@email.com");

        // when
        final HttpRequest actual = HttpRequestParser.parse(bufferedReader);

        // then
        assertThat(actual).isEqualTo(new HttpRequest(requestLine, requestHeaders, requestBody));
    }

    @DisplayName("HTTP 요청 정보를 읽어 세션을 생성한다")
    @Test
    void create_session() throws IOException {
        // given
        String httpRequestMessage = "GET /index.html?userId=admin&email=admin@email.com HTTP/1.1\r\n"
            + "Host: localhost:8080\r\n"
            + "Connection: keep-alive\r\n"
            + "Cookie: JWP_SESSION_ID=12345;\r\n"
            + "\r\n";

        BufferedReader bufferedReader = getBufferedReader(httpRequestMessage);
        HttpRequestParser.parse(bufferedReader);

        // when
        final boolean has = HttpSessionContext.has("12345");

        assertThat(has).isTrue();
    }

    private BufferedReader getBufferedReader(final String httpRequestMessage) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequestMessage.getBytes());
        return new BufferedReader(new InputStreamReader(inputStream));
    }

}
