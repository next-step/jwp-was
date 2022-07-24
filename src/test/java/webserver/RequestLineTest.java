package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestLineTest {
    @Test
    @DisplayName("RequestLine 객체를 생성한다.")
    void create_RequestLine() {
        RequestLine requestLine = new RequestLine("GET", new Path("/users", null), "HTTP", "1.1");
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
    @DisplayName("HTTP 요청 method 가 GET, POST 가 아닐 경우 예외가 발생한다.")
    void throw_exception_request_method_not_GET_or_POST() {
        assertThatThrownBy(() -> RequestLine.parse("GETS /users HTTP/1.1")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("HTTP protocol 과 version 을 '/'으로 구분한 데이터 갯수가 2가 아닐 경우 예외가 발생한다.")
    void throw_exception_protocol_and_version_parse_element_number_not_2() {
        assertThatThrownBy(() -> RequestLine.parse("GET users HTTP/1.1/test")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("HTTP 요청 protocol 이 'HTTP' 가 아닐 경우 예외가 발생한다.")
    void throw_exception_request_protocol_not_HTTP() {
        assertThatThrownBy(() -> RequestLine.parse("GET /users HTTPS/1.1")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("HTTP 요청 version 이 숫자가 아닐 경우 예외가 발생한다.")
    void throw_exception_request_version_not_NUMBER() {
        assertThatThrownBy(() -> RequestLine.parse("GET /users HTTPS/ONE")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("GET 요청에 대한 RequestLine 을 파싱한다.")
    void parse_GET_RequestLine() {
        String request = "GET /users HTTP/1.1";
        RequestLine requestLine = RequestLine.parse(request);
        assertThat(requestLine).isEqualTo(new RequestLine("GET", new Path("/users", null), "HTTP", "1.1"));
    }

    @Test
    @DisplayName("POST 요청에 대한 RequestLine 을 파싱한다.")
    void parse_POST_RequestLine() {
        String request = "POST /users HTTP/1.1";
        RequestLine requestLine = RequestLine.parse(request);
        assertThat(requestLine).isEqualTo(new RequestLine("POST", new Path("/users", null), "HTTP", "1.1"));
    }

    @Test
    @DisplayName("Query String 이 포함된 요청에 대한 RequestLine 을 파싱한다.")
    void parse_Query_String_RequestLine() {
        String request = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";
        RequestLine requestLine = RequestLine.parse(request);
        Map<String, String> queryStrings = Map.of("userId", "javajigi", "password", "password", "name", "JaeSung");
        assertThat(requestLine).isEqualTo(new RequestLine("GET", new Path("/users", new QueryString(queryStrings)), "HTTP", "1.1"));
    }
}
