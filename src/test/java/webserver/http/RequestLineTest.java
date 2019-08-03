package webserver.http;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {
    @Test
    void parseMethod() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo("GET");
    }

    @Test
    void parseOtherMethod() {
        RequestLine requestLine = RequestLine.parse("POST /users HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo("POST");
    }

    @Test
    void parsePath() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");
        assertThat(requestLine.getPath()).isEqualTo("/users");
    }

    @Test
    void parseOtherPath() {
        RequestLine requestLine = RequestLine.parse("GET /products HTTP/1.1");
        assertThat(requestLine.getPath()).isEqualTo("/products");
    }

    @Test
    void parseHttpVersion() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    void parseOtherHttpVersion() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/2");
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/2");
    }

    @Test
    void parseQueryString(){
        RequestLine requestLine = RequestLine.parse("GET /users?id=myId HTTP/1.1");
        assertThat(requestLine.getQueryString()).isEqualTo(QueryString.parse("id=myId"));
    }

    @Test
    void parseOtherQueryString(){
        RequestLine requestLine = RequestLine.parse("GET /users?id=myId&name=myName HTTP/1.1");
        assertThat(requestLine.getQueryString()).isEqualTo(QueryString.parse("id=myId&name=myName"));
    }
}
