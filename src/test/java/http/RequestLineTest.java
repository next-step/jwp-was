package http;

import http.requestline.HttpMethod;
import http.requestline.Protocol;
import http.requestline.ProtocolAndVersion;
import http.requestline.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {
    @DisplayName("RequestLine 생성 - GET")
    @Test
    void create() {
        //given
        String request = "GET /users HTTP/1.1";

        //when
        RequestLine requestLine = RequestLine.parse(request);

        //then
        assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getStringPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocolAndVersion()).isEqualTo(new ProtocolAndVersion(Protocol.HTTP, "1.1"));
    }

    @DisplayName("RequestLine 생성 - GET with QueryStrings")
    @Test
    void parseQueryString() {
        //given
        String request = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        //when
        RequestLine parsedLine = RequestLine.parse(request);

        //then
        assertThat(parsedLine.getQueryStrings())
                .hasSize(3);
        assertThat(parsedLine.getQueryStrings().get("userId"))
                .isEqualTo("javajigi");
        assertThat(parsedLine.getQueryStrings().get("password"))
                .isEqualTo("password");
        assertThat(parsedLine.getQueryStrings().get("name"))
                .isEqualTo("JaeSung");
    }

    @DisplayName("RequestLine 생성 - POST")
    @Test
    void createWhenPost() {
        //given
        String requestLine = "POST /users HTTP/1.1";

        //when
        RequestLine parsedLine = RequestLine.parse(requestLine);

        //then
        assertThat(parsedLine.getHttpMethod())
                .isEqualTo(HttpMethod.POST);
        assertThat(parsedLine.getStringPath())
                .isEqualTo("/users");
        assertThat(parsedLine.getQueryStrings())
                .hasSize(0);
        assertThat(parsedLine.getProtocolAndVersion())
                .isEqualTo(new ProtocolAndVersion(Protocol.HTTP, "1.1"));
    }
}