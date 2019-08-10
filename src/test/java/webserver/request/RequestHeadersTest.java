package webserver.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Created by hspark on 2019-08-04.
 */
class RequestHeadersTest {

    @ParameterizedTest(name = "requestLine: {0}, parseResult : [ method : {1}, path : {2}, params : {3} ]")
    @MethodSource("parseHeader")
    void test_parse_header(String rawHeader, String name, String value) {
        RequestHeaders requestHeaders = new RequestHeaders();
        requestHeaders.add(rawHeader);
        Assertions.assertThat(requestHeaders.getHeader(name)).isEqualTo(value);
    }

    private static Stream<Arguments> parseHeader() {
        return Stream.of(
                Arguments.of("Host: localhost:8080", "Host", "localhost:8080"),
                Arguments.of("Connection: keep-alive", "Connection", "keep-alive"),
                Arguments.of("Accept-Encoding: gzip, deflate, br", "Accept-Encoding", "gzip, deflate, br")
        );
    }
}