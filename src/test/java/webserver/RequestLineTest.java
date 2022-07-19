package webserver;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

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
                () -> assertThat(requestLineParser.getMethod()).isEqualTo("GET"),
                () -> assertThat(requestLineParser.getPath()).isEqualTo("/users"),
                () -> assertThat(requestLineParser.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(requestLineParser.getVersion()).isEqualTo("1.1")
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
                () -> assertThat(requestLineParser.getMethod()).isEqualTo("POST"),
                () -> assertThat(requestLineParser.getPath()).isEqualTo("/users"),
                () -> assertThat(requestLineParser.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(requestLineParser.getVersion()).isEqualTo("1.1")
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
                () -> assertThat(requestLineParser.getQueryString().get("name1")).isEqualTo("value1"),
                () -> assertThat(requestLineParser.getQueryString().get("name2")).isEqualTo("value2")

        );
    }
}
