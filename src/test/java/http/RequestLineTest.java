package http;

import http.request.HttpMethod;
import http.request.Protocol;
import http.request.QueryString;
import http.request.RequestLine;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {
    @Test
    void of_get() throws UnsupportedEncodingException {
        RequestLine requestLine = RequestLine.of("GET /users HTTP/1.1");
        Protocol protocol = requestLine.getProtocol();
        QueryString queryString = requestLine.getQueryString();

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(protocol.getProtocol()).isEqualTo("HTTP");
        assertThat(protocol.getVersion()).isEqualTo("1.1");
        assertThat(queryString.getQueryStringMap()).hasSize(0);
    }

    @Test
    void of_post() throws UnsupportedEncodingException {
        RequestLine requestLine = RequestLine.of("POST /users HTTP/1.1");
        Protocol protocol = requestLine.getProtocol();
        QueryString queryString = requestLine.getQueryString();

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(protocol.getProtocol()).isEqualTo("HTTP");
        assertThat(protocol.getVersion()).isEqualTo("1.1");
        assertThat(queryString.getQueryStringMap()).hasSize(0);
    }

    @Test
    void of_queryString() throws UnsupportedEncodingException {
        RequestLine requestLine = RequestLine.of("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");
        Protocol protocol = requestLine.getProtocol();
        QueryString queryString = requestLine.getQueryString();

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(protocol.getProtocol()).isEqualTo("HTTP");
        assertThat(protocol.getVersion()).isEqualTo("1.1");
        assertThat(queryString.getValue("userId")).isEqualTo("javajigi");
        assertThat(queryString.getValue("password")).isEqualTo("password");
        assertThat(queryString.getValue("name")).isEqualTo("JaeSung");
    }

    @Test
    void getFilePath() throws UnsupportedEncodingException {
        RequestLine requestLine = RequestLine.of("GET /index.html HTTP/1.1");

        assertThat(requestLine.getFilePath()).isEqualTo("./templates/index.html");
    }
}
