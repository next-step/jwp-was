package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import webserver.http.request.RequestHeader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestHeaderTest {

    @DisplayName("parse request header")
    @ParameterizedTest(name = "test: {0} -> result: {1}")
    @MethodSource("sampleHeaders")
    void parseRequestHeader(List<String> headers, Map<String, String> expectedHeaders) {
        RequestHeader requestHeader = RequestHeader.parse(headers);
        assertThat(requestHeader.getHeaders()).containsAllEntriesOf(expectedHeaders);
    }

    private static Stream<Arguments> sampleHeaders() {
        return Stream.of(
                Arguments.of(asList("Host: localhost:8080\n", "Connection: keep-alive\n", "Accept: */*"),
                        new HashMap<String, String>(){{
                            put("Host", "localhost:8080");
                            put("Connection", "keep-alive");
                            put("Accept", "*/*");
                        }})
        );
    }

}
