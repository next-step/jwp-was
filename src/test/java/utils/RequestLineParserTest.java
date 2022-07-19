package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


public class RequestLineParserTest {
    static String GET_REQUEST_LINE = "GET /users HTTP/1.1";
    static String POST_REQUEST_LINE = "POST /users HTTP/1.1";
    static String GET_QUERY_STRING_REQUEST_LINE = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

    @Test
    @DisplayName("Request Line이 올바른 형식을 가지는지 테스트")
    void regexTest() {
        String regex = "[A-Z]* {1}\\S* {1}[A-Z]*\\/[0-9|.]+";
        assertAll(
                () -> assertThat(GET_REQUEST_LINE.matches(regex)).isTrue(),
                () -> assertThat(POST_REQUEST_LINE.matches(regex)).isTrue(),
                () -> assertThat(GET_QUERY_STRING_REQUEST_LINE.matches(regex)).isTrue()
        );
    }

    @Test
    @DisplayName("isRequestLinePattern 메소드는 입력값이 RequestLine 형식 여부를 반환한다.")
    void isRequestLinePatternTest() {
        assertAll(
                () -> assertThat(RequestLineParser.isRequestLinePattern(GET_REQUEST_LINE)).isTrue(),
                () -> assertThat(RequestLineParser.isRequestLinePattern(POST_REQUEST_LINE)).isTrue(),
                () -> assertThat(RequestLineParser.isRequestLinePattern(GET_QUERY_STRING_REQUEST_LINE)).isTrue()
        );
    }
}
