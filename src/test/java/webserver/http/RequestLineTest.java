package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestLineTest {
    @Test
    @DisplayName("RequestLine 객체를 생성한다.")
    void create_RequestLine() {
        RequestLine requestLine = new RequestLine(Method.GET, new Path("/users", null), new Protocol("HTTP", Version.ONE_ONE));
        assertThat(requestLine).isNotNull().isInstanceOf(RequestLine.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("요청 값이 null 이거나 빈 값일 경우 예외가 발생한다.")
    void throw_exception_request_null_or_empty(String request) {
        assertThatThrownBy(() -> RequestLine.parse(request)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("HTTP 요청을 공백으로 구분한 데이터 갯수가 3이 아닐 경우 예외가 발생한다.")
    void throw_exception_request_parse_element_number_not_3() {
        assertThatThrownBy(() -> RequestLine.parse("GET users HTTP/1.1 test")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("GET 요청에 대한 RequestLine 을 파싱한다.")
    void parse_GET_RequestLine() {
        String request = "GET /users HTTP/1.1";
        RequestLine requestLine = RequestLine.parse(request);
        assertThat(requestLine).isEqualTo(new RequestLine(Method.GET, new Path("/users", new QueryString(Collections.EMPTY_MAP)), new Protocol("HTTP", Version.ONE_ONE)));
    }

    @Test
    @DisplayName("POST 요청에 대한 RequestLine 을 파싱한다.")
    void parse_POST_RequestLine() {
        String request = "POST /users HTTP/1.1";
        RequestLine requestLine = RequestLine.parse(request);
        assertThat(requestLine).isEqualTo(new RequestLine(Method.POST, new Path("/users", new QueryString(Collections.EMPTY_MAP)), new Protocol("HTTP", Version.ONE_ONE)));
    }

    @Test
    @DisplayName("Query String 이 포함된 요청에 대한 RequestLine 을 파싱한다.")
    void parse_Query_String_RequestLine() {
        String request = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";
        RequestLine requestLine = RequestLine.parse(request);
        Map<String, String> queryStrings = Map.of("userId", "javajigi", "password", "password", "name", "JaeSung");
        assertThat(requestLine).isEqualTo(new RequestLine(Method.GET, new Path("/users", new QueryString(queryStrings)), new Protocol("HTTP", Version.ONE_ONE)));
    }
}
