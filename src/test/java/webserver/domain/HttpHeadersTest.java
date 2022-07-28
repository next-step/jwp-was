package webserver.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.*;
import static webserver.domain.HttpHeaders.CONTENT_TYPE;

class HttpHeadersTest {
    private final String[] ATTRIBUTES = {"Content-Type: text/html", "Accept: */*", "Content-Length: 10", "Location: korea"};
    private final String COOKIE_LINE = "Cookie: keyA=valueA";
    private final String[][] ATTRIBUTE_PAIR_LIST = new String[ATTRIBUTES.length][2];

    @BeforeEach
    void setUp() {
        for (int i = 0; i < ATTRIBUTES.length; i++) {
            String[] splitAtt = ATTRIBUTES[i].split(HttpHeaders.DELIMITER);

            ATTRIBUTE_PAIR_LIST[i][0] = splitAtt[0];
            ATTRIBUTE_PAIR_LIST[i][1] = splitAtt[1];
        }
    }

    @DisplayName("유효한 속성과 범위를 인수로 전달하면 객체를 생성할 수 있다.")
    @Test
    void newInstanceHttpHeader() {
        HttpHeaders httpHeaders = HttpHeaders.newInstance(ATTRIBUTES, 0, ATTRIBUTES.length);

        for (String[] attPair : ATTRIBUTE_PAIR_LIST) {
            assertThat(httpHeaders.getAttribute(attPair[0])).isEqualTo(attPair[1]);
        }
    }

    @DisplayName("정적 팩토리를 이용해 Content-Type이 text/html인 기본 객체를 생성할 수 있다.")
    @Test
    void defaultResponseHeader() {
        HttpHeaders httpHeaders = HttpHeaders.defaultResponseHeader();

        assertThat(httpHeaders.getAttribute(CONTENT_TYPE)).isEqualTo("text/html");
    }

    @DisplayName("유효한 헤더 속성이 담긴 OutputStream을 읽어서 객체를 생성할 수 있다.")
    @Test
    void fromWithValidOutputStream() throws IOException {
        String attributesLine = Arrays.stream(ATTRIBUTES).collect(Collectors.joining(System.lineSeparator()));
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream(attributesLine.getBytes(StandardCharsets.UTF_8))));

        HttpHeaders httpHeaders = HttpHeaders.from(br);

        for (String[] attPair : ATTRIBUTE_PAIR_LIST) {
            assertThat(httpHeaders.getAttribute(attPair[0])).isEqualTo(attPair[1]);
        }
    }

    @DisplayName("유효한 키/값 쌍을 통해 속성을 추가할 수 있다.")
    @Test
    void addValidPair() {
        HttpHeaders httpHeaders = new HttpHeaders();

        for (String[] pair : ATTRIBUTE_PAIR_LIST) {
            httpHeaders.add(pair[0], pair[1]);
        }

        for (String[] attPair : ATTRIBUTE_PAIR_LIST) {
            assertThat(httpHeaders.getAttribute(attPair[0])).isEqualTo(attPair[1]);
        }
    }

    @DisplayName("유효한 문자열을 통해 속성을 추가할 수 있다.")
    @Test
    void addValidLine() {
        HttpHeaders httpHeaders = new HttpHeaders();

        for (String attribute : ATTRIBUTES) {
            httpHeaders.add(attribute);
        }

        for (String[] attPair : ATTRIBUTE_PAIR_LIST) {
            assertThat(httpHeaders.getAttribute(attPair[0])).isEqualTo(attPair[1]);
        }
    }

    @DisplayName("유효하지 않은 문자열을 전달하면 예외를 던진다. ")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"key", "key:", ":value"})
    void addInvalidLine(String invalidLine){
        HttpHeaders httpHeaders = new HttpHeaders();

        assertThatThrownBy(()-> httpHeaders.add(invalidLine))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(HttpHeaders.INVALID_HEADER_KEY_VALUE_MSG+invalidLine);
    }

    @DisplayName("존재하지 않는 값대신 기본값을 전달할 수 있다.")
    @Test
    void getOrDefaultAttribute() {
        String defaultValue = "test";
        HttpHeaders httpHeaders = new HttpHeaders();

        assertThat(httpHeaders.getAttributeOrDefault("not_exists_attribute", defaultValue)).isEqualTo(defaultValue);
    }


    @DisplayName("쿠키값이 존재할 경우 쿠키값을 담은 쿠키 객체를 반환한다. ")
    @Test
    void getCookieExistsStatus() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(COOKIE_LINE);
        String[] cookieArr = COOKIE_LINE.split(HttpHeaders.DELIMITER);
        String[] pair = cookieArr[1].split(Cookie.KEY_VALUE_DELIMITER);

        Cookie cookie = httpHeaders.getCookie();
        assertThat(cookie.getAttribute(pair[0])).isEqualTo(pair[1]);
    }



}
