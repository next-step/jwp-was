package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import types.HttpMethod;
import types.Protocol;

import java.io.IOException;
import java.util.List;
import java.util.Map;

class HttpRequestMessageTest {

    @DisplayName("httpMessage 생성 검증 - requestLine")
    @Test
    void createHttpMessageRequestLineTest() throws IOException {
        List<String> httpMessageData = List.of("POST /user/create HTTP/1.1");
        HttpRequestMessage httpRequestMessage = new HttpRequestMessage(httpMessageData);

        RequestLine actual = httpRequestMessage.getRequestLine();
        RequestLine expected = new RequestLine(HttpMethod.POST, new UrlPath("/user/create"), Protocol.HTTP_1_1);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("httpMessage 생성 검증 - requestHeaders")
    @Test
    void createHttpMessageRequestLineRequestHeaderTest() throws IOException {
        List<String> httpMessageData = List.of(
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 59",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*"
        );
        HttpRequestMessage httpRequestMessage = new HttpRequestMessage(httpMessageData);

        HttpHeaders actualRequestHeaders = httpRequestMessage.getRequestHeaders();
        Map<String, String> expectedRequestHeaders = Map.of(
                "Host", "localhost:8080",
                "Connection", "keep-alive",
                "Content-Length", "59",
                "Content-Type", "application/x-www-form-urlencoded",
                "Accept", "*/*"
        );
        for (Map.Entry<String, String> entry : actualRequestHeaders.getHeaders().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            Assertions.assertThat(expectedRequestHeaders.get(key)).isEqualTo(value);
        }

    }

}
