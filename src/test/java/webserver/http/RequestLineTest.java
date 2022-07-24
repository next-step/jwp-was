package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.http.RequestTestConstant.*;

public class RequestLineTest {

    private RequestLine requestLine;

    @BeforeEach
    void setup() {
        requestLine = new RequestLine(GET_REQUEST);
    }

    @Test
    @DisplayName("RequestLine을 파싱하여 Method를 가져온다.")
    void parsingRequestLineAndGetMethod() {
        // then
        assertThat(requestLine.method()).isEqualTo(GET_METHOD);
    }

    @Test
    @DisplayName("RequestLine을 파싱하여 path를 가져온다.")
    void parsingRequestLineAndGetPath() {
        // then
        assertThat(requestLine.path()).isEqualTo(PATH);
    }

    @Test
    @DisplayName("RequestLine을 파싱하여 protocol을 가져온다.")
    void parsingRequestLineAndGetProtocol() {
        // when
        RequestLine requestLine = new RequestLine(GET_REQUEST);

        // then
        assertThat(requestLine.protocol()).isEqualTo(PROTOCOL);
    }

    @Test
    @DisplayName("RequestLine을 파싱하여 version을 가져온다.")
    void parsingRequestLineAndGetVersion() {
        // then
        assertThat(requestLine.version()).isEqualTo(VERSION);
    }

    @Test
    @DisplayName("RequestLine을 파싱한 결과 정상 확인")
    void parsingRequestLine() {
        // given
        Protocol protocol = new Protocol(GET_REQUEST.split(REQUEST_SPLIT_SYMBOL)[2]);

        // when
        RequestLine requestLine = new RequestLine(GET_REQUEST);

        // then
        assertThat(requestLine).isEqualTo(new RequestLine(GET_METHOD, PATH, protocol));
    }


}
