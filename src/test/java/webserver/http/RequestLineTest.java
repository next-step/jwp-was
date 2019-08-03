package webserver.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.HttpMethod;
import webserver.RequestLine;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {

    @DisplayName("parse without query")
    @ParameterizedTest(name = "requestLine = {0} -> method : {1}, path : {2}, protocol : {3}, queryMap : {4}")
    @MethodSource("sampleRequestLine")
    void parse(String request,
               HttpMethod expectedHttpMethod,
               String expectedPath,
               String expectedProtocol,
               Map<String, String> expectedQueryMap) {
        RequestLine requestLine = RequestLine.parse(request);
        assertThat(requestLine.getMethod()).isEqualTo(expectedHttpMethod);
        assertThat(requestLine.getPath()).isEqualTo(expectedPath);
        assertThat(requestLine.getProtocol()).isEqualTo(expectedProtocol);
        assertThat(requestLine.getQueryMap()).containsAllEntriesOf(expectedQueryMap);
    }

    @DisplayName("invalid request arguments")
    @ParameterizedTest(name = "requestLine = {0} -> IllegalArgumentException")
    @ValueSource(strings = {"GET /users", "GET HTTP/1.1", "NoOp /users HTTP/1.1"})
    void argumentException(String request) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> RequestLine.parse(request));
    }

    private static Stream<Arguments> sampleRequestLine() {
        return Stream.of(
                Arguments.of("GET /users HTTP/1.1", HttpMethod.GET, "/users", "HTTP/1.1", emptyMap()),
                Arguments.of("GET /users? HTTP/1.1", HttpMethod.GET, "/users", "HTTP/1.1", emptyMap()),
                Arguments.of("GET /users?=test&password=password HTTP/1.1", HttpMethod.GET, "/users", "HTTP/1.1",
                        new HashMap<String, String>(){{
                            put("password", "password");
                        }}),
                Arguments.of("GET /users?userId=&password=password HTTP/1.1", HttpMethod.GET, "/users", "HTTP/1.1",
                        new HashMap<String, String>(){{
                            put("password", "password");
                        }}),
                Arguments.of("GET /users?userId=homelus&password=password&name=HyunJun HTTP/1.1", HttpMethod.GET, "/users", "HTTP/1.1",
                        new HashMap<String, String>(){{
                            put("userId", "homelus");
                            put("password", "password");
                            put("name", "HyunJun");
                        }}));
    }

    @Test
    void httpMethodTest() {
        assertThat(HttpMethod.valueOf("GET")).isEqualTo(HttpMethod.GET);
        Assertions.assertThrows(IllegalArgumentException.class, () -> HttpMethod.valueOf("NoHttpMethod"));
    }

}
