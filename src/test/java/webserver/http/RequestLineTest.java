package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.http.RequestTestConstant.*;

public class RequestLineTest {

    private RequestLine getRequestLine;

    @BeforeEach
    void setup() {
        getRequestLine = new RequestLine(GET_REQUEST);
    }

    @Test
    @DisplayName("RequestLine을 파싱하여 Method를 가져온다.")
    void parsingRequestLineAndGetMethod() {
        // then
        assertThat(getRequestLine.method()).isEqualTo(GET_METHOD);
    }

    @Test
    @DisplayName("RequestLine을 파싱하여 path를 가져온다.")
    void parsingRequestLineAndGetPath() {
        // then
        assertThat(getRequestLine.path()).isEqualTo(PATH);
    }

    @Test
    @DisplayName("RequestLine을 파싱하여 protocol을 가져온다.")
    void parsingRequestLineAndGetProtocol() {
        // then
        assertThat(getRequestLine.protocol()).isEqualTo(PROTOCOL);
    }

    @Test
    @DisplayName("RequestLine을 파싱하여 version을 가져온다.")
    void parsingRequestLineAndGetVersion() {
        // then
        assertThat(getRequestLine.version()).isEqualTo(VERSION);
    }

    @Test
    @DisplayName("GET RequestLine을 파싱한 결과 정상 확인")
    void parsingGetRequestLine() {
        // given
        Protocol protocol = new Protocol(GET_REQUEST.split(REQUEST_SPLIT_SYMBOL)[2]);

        // when
        RequestLine requestLine = new RequestLine(GET_REQUEST);

        // then
        assertThat(requestLine).isEqualTo(new RequestLine(GET_METHOD, PATH, protocol));
    }


    @Test
    @DisplayName("POST RequestLine을 파싱한 결과 정상 확인")
    void parsingPostRequestLine() {
        // given
        Protocol protocol = new Protocol(POST_REQUEST.split(REQUEST_SPLIT_SYMBOL)[2]);

        // when
        RequestLine requestLine = new RequestLine(POST_REQUEST);

        // then
        assertThat(requestLine).isEqualTo(new RequestLine(POST_METHOD, PATH, protocol));
    }
}
