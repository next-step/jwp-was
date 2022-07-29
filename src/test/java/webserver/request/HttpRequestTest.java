package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestTest {

    @DisplayName("HTTP GET 요청 정보를 생성한다")
    @Test
    void create_http_request_with_get() {

        final RequestLine requestLine = RequestLine.of("GET", "/index.html?userId=admin&email=admin@email.com", "HTTP/1.1");
        final RequestHeaders requestHeaders = new RequestHeaders();
        requestHeaders.add("Host: localhost:8080");
        requestHeaders.add("Connection: keep-alive");
        RequestBody requestBody = RequestBody.EMPTY_REQUEST_BODY;

        final HttpRequest httpRequest = new HttpRequest(requestLine, requestHeaders, requestBody);

        assertThat(httpRequest).isEqualTo(new HttpRequest(requestLine, requestHeaders, requestBody));

    }

    @DisplayName("HTTP POST 요청 정보를 생성한다")
    @Test
    void create_http_request_with_post() {

        final RequestLine requestLine = RequestLine.of("POST", "/index.html", "HTTP/1.1");
        final RequestHeaders requestHeaders = new RequestHeaders();
        requestHeaders.add("Host: localhost:8080");
        requestHeaders.add("Connection: keep-alive");
        requestHeaders.add("Content-Length: 34");
        RequestBody requestBody = new RequestBody("userId=admin&email=admin@email.com");

        final HttpRequest httpRequest = new HttpRequest(requestLine, requestHeaders, requestBody);

        assertThat(httpRequest).isEqualTo(new HttpRequest(requestLine, requestHeaders, requestBody));

    }

}
