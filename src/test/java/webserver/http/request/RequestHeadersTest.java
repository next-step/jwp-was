package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpHeader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.http.HttpHeader.*;

public class RequestHeadersTest {

    @DisplayName("request header 파싱")
    @Test
    void parse() {

        // given
        List<String> requestHeaderTexts = Arrays.asList(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: text/html");

        Map<HttpHeader, RequestHeader> requestHeaderMap = new HashMap<>();
        requestHeaderMap.put(HOST, RequestHeader.of("Host: localhost:8080"));
        requestHeaderMap.put(CONNECTION, RequestHeader.of("Connection: keep-alive"));
        requestHeaderMap.put(ACCEPT, RequestHeader.of("Accept: text/html"));


        // when
        RequestHeaders requestHeaders = RequestHeaders.of(requestHeaderTexts);

        // then
        assertThat(requestHeaders)
                .isEqualTo(new RequestHeaders(requestHeaderMap));
    }

    @DisplayName("request header의 속성 확인")
    @Test
    void get() {

        // given
        List<String> requestHeaderTexts = Arrays.asList(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: text/html");

        // when
        RequestHeaders requestHeaders = RequestHeaders.of(requestHeaderTexts);

        // then
        assertThat(requestHeaders.get(ACCEPT))
                .isEqualTo("text/html");
    }
}
