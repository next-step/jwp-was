package webserver.http.cookie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import webserver.http.domain.cookie.Cookie;

import java.util.Date;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CookieTest {
    @DisplayName("value를 문자열로 받아 쿠키 객체 생성")
    @Test
    void of_string_value() {
        Cookie actual = Cookie.of("name", "value");
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(new Cookie("name", "value"));
    }

    @DisplayName("value를 boolean타입으로 받아 쿠키 객체 생성")
    @Test
    void of_boolean_value() {
        Cookie actual = Cookie.of("logined", false);
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(new Cookie("logined", "false"));
    }

    @DisplayName("쿠키 객체를 Http Response Header중, Set-Cookie 값에 세팅될 문자열 값으로 변환")
    @ParameterizedTest
    @MethodSource("provideForGetAsHeaderValue")
    void getAsHeaderValue(Cookie cookie, String expected) {
        String actual = cookie.getAsHeaderValue();
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> provideForGetAsHeaderValue() {
        return Stream.of(
                arguments(
                        Cookie.builder("JWP_SID", "11234567")
                                .domain("localhost")
                                .path("/path")
                                .build(),
                        "JWP_SID=11234567; Path=/path; Domain=localhost"
                ),
                arguments(
                        Cookie.builder("JWP_SID", "11234567")
                                .domain("localhost")
                                .path("/path")
                                .maxAge(300)
                                .secure(true)
                                .httpOnly(true)
                                .build(),
                        "JWP_SID=11234567; Path=/path; Max-Age=300; Domain=localhost; HttpOnly; Secure"
                ),
                arguments(
                        Cookie.builder("JWP_SID", "11234567")
                                .domain("localhost")
                                .path("/path")
                                .sameSite("Strict")
                                .maxAge(300)
                                .expires(new Date(1_500_000_000_000L))
                                .secure(true)
                                .httpOnly(true)
                                .build(),
                        "JWP_SID=11234567; Path=/path; Max-Age=300; Expires=Fri Jul 14 11:40:00 KST 2017; Domain=localhost; SameSite=Strict; HttpOnly; Secure"
                )
        );
    }
}