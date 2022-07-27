package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class HeadersTest {

    @DisplayName("Header에 Content-Length 키의 포함여부를 반환")
    @ParameterizedTest
    @MethodSource("provideForContainsContentLength")
    void containsContentLength(Headers headers, boolean expected) {
        boolean actual = headers.containsContentLength();
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> provideForContainsContentLength() {
        HashMap<String, String> keyValues = new HashMap<>();
        keyValues.put("Content-Length", "13");
        return Stream.of(
                arguments(new Headers(new HashMap<>()), false),
                arguments(new Headers(keyValues), true)
        );
    }

    @DisplayName("Header에 Content-Length 키의 value를 int형으로 반환")
    @ParameterizedTest
    @MethodSource("provideForGetContentLength")
    void getContentLength(Headers headers, int expected) {
        int actual = headers.getContentLength();
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> provideForGetContentLength() {
        HashMap<String, String> keyValues = new HashMap<>();
        keyValues.put("Content-Length", "13");
        return Stream.of(
                arguments(new Headers(new HashMap<>()), 0),
                arguments(new Headers(keyValues), 13)
        );
    }

    @DisplayName("Header에 해당하는 Content-Type 값이 존재하는지 검증")
    @ParameterizedTest
    @MethodSource("provideForHasContentType")
    void hasContentType(Headers headers, String contentType, boolean expected) {
        boolean actual = headers.hasContentType(contentType);
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> provideForHasContentType() {
        HashMap<String, String> keyValues = new HashMap<>();
        keyValues.put("Content-Length", "13");
        return Stream.of(
                arguments(new Headers(Map.of("Content-Type", "application/json")), "application/json", true),
                arguments(new Headers(Map.of("Content-Type", "application/json")), "text/html", false),
                arguments(new Headers(Map.of()), "text/html", false)
        );
    }
}