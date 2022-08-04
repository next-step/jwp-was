package webserver.http.cookie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import webserver.http.domain.cookie.Cookie;
import webserver.http.domain.cookie.Cookies;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CookiesTest {

    @DisplayName("key1=value1; key2=value2; ... 형식으로 이뤄진 문자열을 Cookies 객체로 파싱한다.")
    @Test
    void from() {
        String message = "Idea=7d39d793-8ebb-45b8-9e1e-7c66b1449982; logined=true; other=";
        Cookies actual = Cookies.from(message);

        assertThat(actual).usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(new Cookies(
                        Map.of(
                                "Idea", new Cookie("Idea", "7d39d793-8ebb-45b8-9e1e-7c66b1449982"),
                                "logined", new Cookie("logined", "true"),
                                "other", new Cookie("other", "")
                        )
                ));
    }

    @DisplayName("null 혹은 빈문자열은 빈 Cookies 객체로 파싱한다.")
    @ParameterizedTest
    @EmptySource
    @NullSource
    void from_null(String message) {
        Cookies actual = Cookies.from(message);

        assertThat(actual).usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(new Cookies(
                        Map.of()
                ));
    }

    @DisplayName("nam, valuee가 일치하는 쿠키의 존재여부를 반환")
    @ParameterizedTest
    @CsvSource(value = {"logined, true, true", "invalid, value, false", "logined, false, false"})
    void existsCookie(String name, String value, boolean expected) {
        Cookies cookies = new Cookies(
                Map.of(
                        "Idea", new Cookie("Idea", "7d39d793-8ebb-45b8-9e1e-7c66b1449982"),
                        "logined", new Cookie("logined", "true"),
                        "other", new Cookie("other", "")
                )
        );

        boolean actual = cookies.existsCookie(name, value);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("Cookies에서 해당이름의 쿠키값을 Optional로 반환")
    @ParameterizedTest
    @MethodSource("provideForGetCookie")
    void getCookie(String name, Optional<Cookie> expected) {
        Cookies cookies = Cookies.from("name1=value1; name2=value2");

        Optional<Cookie> actual = cookies.getCookie(name);
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