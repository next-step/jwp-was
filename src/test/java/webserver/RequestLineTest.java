package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {
    @Test
    void parse() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/users");
    }

    @Test  // step1 - req1
    void queryString() {
        RequestLine requestLine = RequestLine.parse("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getQueryString()).isEqualTo("userId=javajigi&password=password&name=JaeSung");
        assertThat(requestLine.getParameters().get("userId")).isEqualTo("javajigi");
        assertThat(requestLine.getParameters().get("password")).isEqualTo("password");
        assertThat(requestLine.getParameters().get("name")).isEqualTo("JaeSung");
    }



}


