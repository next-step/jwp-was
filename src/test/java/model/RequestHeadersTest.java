package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Map;

class RequestHeadersTest {

    @DisplayName("요청 헤더 양식에 맞지 않는 데이터가 들어올 경우에 대한 에러 검증")
    @ParameterizedTest
    @ValueSource(strings = {"Connection:keep-alive", "Connectionkeep-alive", "Connection :keep-alive"})
    void validateTest(String wrongHeader) {
        Assertions.assertThatThrownBy(() -> new RequestHeaders(List.of(wrongHeader))).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("requestHeaders 생성 검증")
    @Test
    void createRequestHeaderTest() {
        List<String> httpMessageData = List.of("Host: localhost:8080", "Connection: keep-alive", "Accept: */*");
        Map<String, String> headers = Map.of("Host", "localhost:8080", "Connection", "keep-alive", "Accept", "*/*");

        RequestHeaders requestHeaders = new RequestHeaders(httpMessageData);
        for (Map.Entry<String, String> entry : requestHeaders.getRequestHeaders().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            Assertions.assertThat(headers.get(key)).isEqualTo(value);
        }
    }

}
