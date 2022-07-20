package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {

    @Test
    void requestLineFromGetRequest() {
        String request = "GET /users HTTP/1.1";
        RequestLine requestLine = new RequestLine(request);

        assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getRequestPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @Test
    void requestLineFromPostRequest() {
        String request = "POST /users HTTP/1.1";
        RequestLine requestLine = new RequestLine(request);

        assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getRequestPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

}