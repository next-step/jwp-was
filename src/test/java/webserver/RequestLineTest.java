package webserver;

import exception.InvalidRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestLineTest {

    @DisplayName("요구사항 1 - GET 요청")
    @Test
    void requestLineFromGetRequest() {
        String request = "GET /users HTTP/1.1";
        RequestLine requestLine = new RequestLine(request);

        assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @DisplayName("요구사항 2 - POST 요청")
    @Test
    void requestLineFromPostRequest() {
        String request = "POST /users HTTP/1.1";
        RequestLine requestLine = new RequestLine(request);

        assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @DisplayName("요구사항 3 - Query String 파싱")
    @Test
    void requestLineFromGetRequest_WithQueryString() {
        String request = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";
        Map<String, String> queryString = Map.of(
                "userId", "javajigi",
                "password", "password",
                "name", "JaeSung"
        );
        RequestLine requestLine = new RequestLine(request);

        assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getQueryString()).isEqualTo(queryString);
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @DisplayName("잘못된 경로로 요청")
    @Test
    void requestLineFromGetRequest_WithWrongPath() {
        String request = "GET  HTTP/1.1";
        assertThatThrownBy(() -> new RequestLine(request))
                .isInstanceOf(InvalidRequestException.class);
    }

}