package http;

import http.request.RequestLine;
import http.request.RequestLineParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class RequestLineParserTest {

    private String getRequest;
    private String postRequest;

    @BeforeEach
    void setUp() {
        getRequest = "GET /users HTTP/1.1";
        postRequest = "POST /users HTTP/1.1";
    }

    @Test
    @DisplayName("요구사항 1 - GET 요청")
    void parseGet() {
        RequestLine requestLine = RequestLineParser.parse(getRequest);
        assertAll("RequestLine 객체의 책임 확인",
                () -> assertThat(requestLine.getMethodName()).isEqualTo("GET"),
                () -> assertThat(requestLine.getPath()).isEqualTo("/users"),
                () -> assertThat(requestLine.getProtocolName()).isEqualTo("HTTP"),
                () -> assertThat(requestLine.getProtocolVersion()).isEqualTo("1.1")
        );
    }

    @Test
    @DisplayName("요구사항 2 - POST 요청")
    void parsePost() {
        RequestLine requestLine = RequestLineParser.parse(postRequest);
        assertAll("RequestLine 객체의 책임 확인",
                () -> assertThat(requestLine.getMethodName()).isEqualTo("POST"),
                () -> assertThat(requestLine.getPath()).isEqualTo("/users"),
                () -> assertThat(requestLine.getProtocolName()).isEqualTo("HTTP"),
                () -> assertThat(requestLine.getProtocolVersion()).isEqualTo("1.1")
        );
    }
}
