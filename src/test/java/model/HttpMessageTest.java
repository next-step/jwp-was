package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import types.HttpMethod;
import types.Protocol;

import java.util.Collections;
import java.util.List;
import java.util.Map;

class HttpMessageTest {

    private final String BODY_SEPARATOR = "";

    @DisplayName("format validation 검증")
    @Test
    void validationTest() {
        List<String> wrongHttpMessageData1 = Collections.emptyList();
        Assertions.assertThatThrownBy(() -> new HttpMessage(wrongHttpMessageData1))
                .isInstanceOf(IllegalArgumentException.class);

        List<String> wrongHttpMessageData2 = List.of(
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 59",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*",
                BODY_SEPARATOR,
                BODY_SEPARATOR,
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"
        );
        Assertions.assertThatThrownBy(() -> new HttpMessage(wrongHttpMessageData2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("httpMessage 생성 검증 - requestLine")
    @Test
    void createHttpMessageRequestLineTest() {
        List<String> httpMessageData = List.of("POST /user/create HTTP/1.1");
        HttpMessage httpMessage = new HttpMessage(httpMessageData);

        RequestLine actual = httpMessage.getRequestLine();
        RequestLine expected = new RequestLine(HttpMethod.POST, new UrlPath("/user/create"), Protocol.HTTP_1_1);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("httpMessage 생성 검증 - requestHeaders")
    @Test
    void createHttpMessageRequestLineRequestHeaderTest() {
        List<String> httpMessageData = List.of(
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 59",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*"
        );
        HttpMessage httpMessage = new HttpMessage(httpMessageData);

        RequestHeaders actualRequestHeaders = httpMessage.getRequestHeaders();
        Map<String, String> expectedRequestHeaders = Map.of(
                "Host", "localhost:8080",
                "Connection", "keep-alive",
                "Content-Length", "59",
                "Content-Type", "application/x-www-form-urlencoded",
                "Accept", "*/*"
        );
        for (Map.Entry<String, String> entry : actualRequestHeaders.getRequestHeaders().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            Assertions.assertThat(expectedRequestHeaders.get(key)).isEqualTo(value);
        }

    }

    @DisplayName("httpMessage 생성 검증 - body")
    @Test
    void createHttpMessageTest() {
        String bodyMessage = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        List<String> httpMessageData = List.of(
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 59",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*",
                BODY_SEPARATOR,
                bodyMessage
        );

        HttpMessage httpMessage = new HttpMessage(httpMessageData);

        String actual = bodyMessage;
        String expected = httpMessage.getBody();

        Assertions.assertThat(actual).isEqualTo(expected);
    }

}
