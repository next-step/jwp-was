package http;

import http.requestline.HttpMethod;
import http.requestline.Protocol;
import http.requestline.ProtocolAndVersion;
import http.requestline.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;

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

    @DisplayName("BufferedReader를 입력으로 받아서 객체 생성")
    @Test
    void createByBufferedReader() {
        //given
        String stringRequest = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Reader reader = new StringReader(stringRequest);

        //when
        RequestLine requestLine = new RequestLine(new BufferedReader(reader));

        //then
        assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getStringPath()).isEqualTo("/user/create");
        assertThat(requestLine.getProtocolAndVersion().getProtocol()).isEqualTo(Protocol.HTTP);
        assertThat(requestLine.getProtocolAndVersion().getVersion()).isEqualTo("1.1");
    }
}