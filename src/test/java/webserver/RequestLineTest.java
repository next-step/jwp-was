package webserver;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import error.NotHttpMethodConstantException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

public class RequestLineTest {
    @Test
    @DisplayName("GET요청에 대한 RequestLine을 파싱한다.")
    void GET요청_파싱_테스트() {
        //given
        String requestLine = "GET /users HTTP/1.1";
        //when
        RequestLineParser requestLineParser = new RequestLineParser(requestLine);
        //then
        assertAll(
                () -> assertThat(requestLineParser.getMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(requestLineParser.getUri().getPath()).isEqualTo("/users"),
                () -> assertThat(requestLineParser.getProtocolAndVersion().getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(requestLineParser.getProtocolAndVersion().getVersion()).isEqualTo("1.1")
        );
    }

    @Test
    @DisplayName("POST요청에 대한 RequestLine을 파싱한다.")
    void POST요청_파싱_테스트() {
        //given
        String requestLine = "POST /users HTTP/1.1";
        //when
        RequestLineParser requestLineParser = new RequestLineParser(requestLine);
        //then
        assertAll(
                () -> assertThat(requestLineParser.getMethod()).isEqualTo(HttpMethod.POST),
                () -> assertThat(requestLineParser.getUri().getPath()).isEqualTo("/users"),
                () -> assertThat(requestLineParser.getProtocolAndVersion().getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(requestLineParser.getProtocolAndVersion().getVersion()).isEqualTo("1.1")
        );
    }

    @Test
    @DisplayName("HTTP요청의 QueryString으로 전달되는 데이터를 파싱한다.")
    void HTTP요청_QueryString_파싱_테스트() {
        //given
        String requestLine = "POST /users?name1=value1&name2=value2 HTTP/1.1";
        //when
        RequestLineParser requestLineParser = new RequestLineParser(requestLine);
        //then
        assertAll(
                () -> assertThat(requestLineParser.getUri().getQueryString().getQueryParameters().get("name1")).isEqualTo("value1"),
                () -> assertThat(requestLineParser.getUri().getQueryString().getQueryParameters().get("name2")).isEqualTo("value2")
        );
    }

    @Test
    @DisplayName("POST, GET이 아닌 HttpMethod가 들어갈 경우, NotHttpMethodConstantException이 발생한다.")
    void HTTP메소드_실패_테스트() {
        //given
        String requestLine = "PUT /users?name1=value1&name2=value2 HTTP/1.1";
        //
        Assertions.assertThrows(NotHttpMethodConstantException.class, () ->
                new RequestLineParser(requestLine));
    }
}
