package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHeadersTest {

    @DisplayName("request header 파싱")
    @Test
    void parse() {

        // given
        List<String> requestHeaderTexts = Arrays.asList(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: text/html");

        Map<String, RequestHeader> requestHeaderMap = new HashMap<>();
        requestHeaderMap.put("Host", RequestHeader.of("Host: localhost:8080"));
        requestHeaderMap.put("Connection", RequestHeader.of("Connection: keep-alive"));
        requestHeaderMap.put("Accept", RequestHeader.of("Accept: text/html"));


        // when
        RequestHeaders requestHeaders = RequestHeaders.of(requestHeaderTexts);

        // then
        assertThat(requestHeaders)
                .isEqualTo(new RequestHeaders(requestHeaderMap));
    }
}
