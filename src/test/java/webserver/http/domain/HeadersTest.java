package webserver.http.domain;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.domain.cookie.Cookie;
import webserver.http.domain.cookie.Cookies;
import webserver.http.domain.exception.BadRequestException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static webserver.http.domain.ContentType.HTML;
import static webserver.http.domain.ContentType.JSON;
import static webserver.http.domain.Headers.ACCEPT;
import static webserver.http.domain.Headers.CONTENT_LENGTH;
import static webserver.http.domain.Headers.CONTENT_TYPE;
import static webserver.http.domain.Headers.COOKIE;

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
        keyValues.put(CONTENT_LENGTH, "13");
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
        keyValues.put(CONTENT_LENGTH, "13");
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
                arguments(new Headers(Map.of(CONTENT_TYPE, JSON.getHeader())), JSON.getHeader(), true),
                arguments(new Headers(Map.of(CONTENT_TYPE, JSON.getHeader())), HTML.getHeader(), false),
                arguments(new Headers(Map.of()), HTML.getHeader(), false)
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
                arguments(new Headers(Map.of(CONTENT_TYPE, JSON.getHeader())), CONTENT_TYPE, JSON.getHeader()),
                arguments(new Headers(Map.of(ACCEPT, JSON.getHeader())), ACCEPT, JSON.getHeader()),
                arguments(new Headers(Map.of()), CONTENT_TYPE, null)
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
                arguments(new Headers(Map.of(CONTENT_TYPE, JSON.getHeader())), CONTENT_TYPE, true),
                arguments(new Headers(Map.of(ACCEPT, JSON.getHeader())), CONTENT_TYPE, false),
                arguments(new Headers(Map.of()), CONTENT_TYPE, false)
        );
    }

    @DisplayName("새로운 헤더를 추가한다. 만약 동일한 name의 헤더를 추가하는 경우 기존의 값을 덮어쓴다.")
    @ParameterizedTest
    @MethodSource("provideForAdd")
    void add(String name, String value, Headers expected) {
        Headers headers = new Headers(
                new HashMap<>(
                        Map.of(CONTENT_TYPE, JSON.getHeader())
                )
        );
        headers.add(name, value);

        assertThat(headers).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    public static Stream<Arguments> provideForAdd() {
        return Stream.of(
                arguments(CONTENT_LENGTH, "26",
                        new Headers(
                                Map.of(
                                        CONTENT_TYPE, JSON.getHeader(),
                                        CONTENT_LENGTH, "26"
                                )
                        )
                ),
                arguments(CONTENT_TYPE, HTML.getHeader(),
                        new Headers(
                                Map.of(CONTENT_TYPE, HTML.getHeader())
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
        keyValues.put(CONTENT_LENGTH, "23");
        return new Headers(keyValues);
    }

    @DisplayName("Cookie 헤터에 인자로 넘어온 이름과 값의 쿠키의 존재여부 반환 ")
    @ParameterizedTest
    @CsvSource(value = {"logined, true, true", "logined, false, false", "other, true, false"})
    void existsCookies(String name, String value, boolean expected) {
        Headers headers = new Headers(
                Map.of(
                        COOKIE, "logined=true; yes=no"
                )
        );

        boolean actual = headers.existsCookie(name, value);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("header의 값이 숫자형인 경우, int 형을 반환")
    @Test
    void getValueAsInt() {
        Headers headers = Headers.from(List.of(
                "Content-Length: 10"
        ));

        int actual = headers.getValueAsInt("Content-Length");
        assertThat(actual).isEqualTo(10);
    }

    @DisplayName("header의 값이 숫자형이 아닌 경우, 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"Accept", "INVALID"})
    void getValueAsInt_fail(String name) {
        Headers headers = Headers.from(List.of(
                "Content-Length: 10",
                "Accept: */*"
        ));

        assertThatThrownBy(() -> headers.getValueAsInt(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("숫자방식이 아닌 리터럴 값은 인자로 들어갈 수 없습니다.");
    }

    @DisplayName("헤더에서 쿠키 목록을 추출한다.")
    @Test
    void getCookies() {
        Headers headers = Headers.from(
                List.of(
                        "Cookie: name1=value1; name2=value2"
                )
        );

        Cookies actual = headers.getCookies();

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(
                        new Cookies(
                                Map.of(
                                        "name1", Cookie.of("name1", "value1"),
                                        "name2", Cookie.of("name2", "value2")
                                )
                        )
                );
    }

    @DisplayName("Request에 해당이름의 쿠키값을 Optional로 반환")
    @ParameterizedTest
    @MethodSource("provideForGetCookie")
    void getCookie(String name, Optional<Cookie> expected) {
        Headers headers = Headers.from(
                List.of(
                        "Cookie: name1=value1; name2=value2"
                )
        );

        Optional<Cookie> actual = headers.getCookie(name);
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    public static Stream<Arguments> provideForGetCookie() {
        return Stream.of(
                arguments("name1", Optional.of(Cookie.of("name1", "value1"))),
                arguments("name2", Optional.of(Cookie.of("name2", "value2"))),
                arguments("name3", Optional.empty())
        );
    }
}