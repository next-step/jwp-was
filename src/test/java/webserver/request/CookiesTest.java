package webserver.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Created by hspark on 2019-08-06.
 */
class CookiesTest {
    @ParameterizedTest(name = "cookies: {0}, [ key : {1}, value : {2}]")
    @MethodSource("parseHeader")
    void test_parse_header(String rawCookie, String name, String value) {
        Cookies cookies = new Cookies();
        cookies.addCookieByRawString(rawCookie);
        Assertions.assertThat(cookies.getCookie(name)).isEqualTo(value);
    }

    private static Stream<Arguments> parseHeader() {
        return Stream.of(
                Arguments.of("logined=true", "logined", "true"),
                Arguments.of("JSESSIONID=1234", "JSESSIONID", "1234")
        );
    }

}