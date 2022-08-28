package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.domain.Protocol;
import webserver.http.domain.RequestUrl;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.http.RequestTestConstant.*;

public class RequestUrlTest {

    private RequestUrl getRequestUrl;

    @BeforeEach
    void setup() {
        getRequestUrl = new RequestUrl(GET_REQUEST);
    }

    @Test
    @DisplayName("RequestLine을 파싱하여 Method를 가져온다.")
    void parsingRequestLineAndGetMethod() {
        // then
        assertThat(getRequestUrl.method()).isEqualTo(GET_METHOD);
    }

    @Test
    @DisplayName("RequestLine을 파싱하여 path를 가져온다.")
    void parsingRequestLineAndGetPath() {
        // then
        assertThat(getRequestUrl.path()).isEqualTo(PATH);
    }

    @Test
    @DisplayName("RequestLine을 파싱하여 protocol을 가져온다.")
    void parsingRequestLineAndGetProtocol() {
        // then
        assertThat(getRequestUrl.protocol()).isEqualTo(PROTOCOL);
    }

    @Test
    @DisplayName("RequestLine을 파싱하여 version을 가져온다.")
    void parsingRequestLineAndGetVersion() {
        // then
        assertThat(getRequestUrl.version()).isEqualTo(VERSION);
    }

    @Test
    @DisplayName("Parameter가 포함 된 RequestLine을 파싱하여 request params를 가져온다.")
    void parsingRequestParameterLineAndGetQueryString() {
        // when
        RequestUrl requestUrl = new RequestUrl(GET_REQUEST_QUERY_STRING);
        // then
        assertThat(requestUrl.requestParams()).isNotEmpty();
    }

    @Test
    @DisplayName("GET RequestLine을 파싱한 결과 정상 확인")
    void parsingGetRequestLine() {
        // given
        Protocol protocol = new Protocol(GET_REQUEST.split(REQUEST_SPLIT_SYMBOL)[2]);

        // when
        RequestUrl requestUrl = new RequestUrl(GET_REQUEST);

        // then
        assertThat(requestUrl).isEqualTo(new RequestUrl(GET_METHOD, PATH, protocol));
    }


    @Test
    @DisplayName("POST RequestLine을 파싱한 결과 정상 확인")
    void parsingPostRequestLine() {
        // given
        Protocol protocol = new Protocol(POST_REQUEST.split(REQUEST_SPLIT_SYMBOL)[2]);

        // when
        RequestUrl requestUrl = new RequestUrl(POST_REQUEST);

        // then
        assertThat(requestUrl).isEqualTo(new RequestUrl(POST_METHOD, PATH, protocol));
    }
}
