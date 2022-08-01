package webserver.http.domain;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import webserver.http.domain.Headers;
import webserver.http.domain.exception.BadRequestException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        return Stream.of(
                arguments(new Headers(Map.of("Content-Type", "application/json")), "application/json", true),
                arguments(new Headers(Map.of("Content-Type", "application/json")), "text/html", false),
                arguments(new Headers(Map.of()), "text/html", false)
        );
    }

    @DisplayName("특정한 key의 헤더가 포함되어있는지 여부를 확인한다.")
    @ParameterizedTest
    @MethodSource("provideForGetValue")
    void getValue(Headers headers, String name, String expected) {
        String actual = headers.getValue(name);
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> provideForGetValue() {
        return Stream.of(
                arguments(new Headers(Map.of("Content-Type", "application/json")), "Content-Type", "application/json"),
                arguments(new Headers(Map.of("Accept", "application/json")), "Accept", "application/json"),
                arguments(new Headers(Map.of()), "Content-Type", null)
        );
    }

    @DisplayName("특정한 key의 헤더가 포함되어있는지 여부를 확인한다.")
    @ParameterizedTest
    @MethodSource("provideForContains")
    void contains(Headers headers, String name, boolean expected) {
        boolean actual = headers.contains(name);
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> provideForContains() {
        return Stream.of(
                arguments(new Headers(Map.of("Content-Type", "application/json")), "Content-Type", true),
                arguments(new Headers(Map.of("Accept", "application/json")), "Content-Type", false),
                arguments(new Headers(Map.of()), "Content-Type", false)
        );
    }

    @DisplayName("새로운 헤더를 추가한다. 만약 동일한 name의 헤더를 추가하는 경우 기존의 값을 덮어쓴다.")
    @ParameterizedTest
    @MethodSource("provideForAdd")
    void add(String name, String value, Headers expected) {
        Headers headers = new Headers(
                new HashMap<>(
                        Map.of("Content-Type", "application/json")
                )
        );
        headers.add(name, value);

        assertThat(headers).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    public static Stream<Arguments> provideForAdd() {
        return Stream.of(
                arguments("Content-Length", "26",
                        new Headers(
                                Map.of(
                                        "Content-Type", "application/json",
                                        "Content-Length", "26"
                                )
                        )
                ),
                arguments("Content-Type", "text/html",
                        new Headers(
                                Map.of("Content-Type", "text/html")
                        )
                )
        );
    }

    @DisplayName("Header 문자열이 {key}: {value} 형식으로 이뤄지지 않은 경우, 예외 발생")
    @Test
    void parse_fail() {
        List<String> messages = Lists.list(
                "Host: localhost:8080",
                "Connection"
        );

        assertThatThrownBy(() -> Headers.from(messages))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("'key=value' 방식의 값이 아닙니다.");
    }

    @DisplayName("Header 문자열이 {key}: {value} 형식으로 이뤄진 경우, Headers 객체 생성")
    @Test
    void parse() {
        List<String> messages = Lists.list(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36",
                "Content-Length: 23"
        );

        Headers actual = Headers.from(messages);
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(fixtureHeader());
    }

    private Headers fixtureHeader() {
        HashMap<String, String> keyValues = new HashMap<>();
        keyValues.put("Host", "localhost:8080");
        keyValues.put("Connection", "keep-alive");
        keyValues.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
        keyValues.put("Content-Length", "23");
        return new Headers(keyValues);
    }
}