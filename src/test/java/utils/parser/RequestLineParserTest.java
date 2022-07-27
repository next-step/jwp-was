package utils.parser;

import enums.HttpMethod;
import model.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.parser.RequestLineParser;

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

    @Test
    @DisplayName("requestLine을 method, path(request-target), protocol, version으로 분리 할 수 있다.")
    void parsingTest() {
        //given
        String path = "/users";
        String protocolType = "HTTP";
        String protocolVersion = "1.1";

        //when
        RequestLine getRequestLineResult = RequestLineParser.parse(GET_REQUEST_LINE);
        RequestLine postRequestLineResult = RequestLineParser.parse(POST_REQUEST_LINE);

        //then
        assertAll(
                () -> assertThat(getRequestLineResult.getHttpMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(getRequestLineResult.getPath()).isEqualTo(path),
                () -> assertThat(getRequestLineResult.getWebProtocol().getType()).isEqualTo(protocolType),
                () -> assertThat(getRequestLineResult.getWebProtocol().getVersion()).isEqualTo(protocolVersion),

                () -> assertThat(postRequestLineResult.getHttpMethod()).isEqualTo(HttpMethod.POST),
                () -> assertThat(postRequestLineResult.getPath()).isEqualTo(path),
                () -> assertThat(postRequestLineResult.getWebProtocol().getType()).isEqualTo(protocolType),
                () -> assertThat(postRequestLineResult.getWebProtocol().getVersion()).isEqualTo(protocolVersion)
        );
    }

    @Test
    @DisplayName("requestLine parsing시, queryString이 포함되어 있다면 queryString도 파싱한다.")
    void queryStringParsingTest() {
        //given
        String path = "/users?userId=javajigi&password=password&name=JaeSung";
        String queryString = "userId=javajigi&password=password&name=JaeSung";

        //when
        RequestLine queryStringRequestLineResult = RequestLineParser.parse(GET_QUERY_STRING_REQUEST_LINE);

        //then
        assertAll(
                () -> assertThat(queryStringRequestLineResult.getPath()).isEqualTo(path),
                () -> assertThat(queryStringRequestLineResult.getQueryString()).isEqualTo(queryString)
        );
    }
}
