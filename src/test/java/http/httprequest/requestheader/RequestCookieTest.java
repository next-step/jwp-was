package http.httprequest.requestheader;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


class RequestCookieTest {

    @Test
    @DisplayName("문자열 리스트로 생성")
    void create() {
        RequestCookie cookie = RequestCookie.from("JSESSIONID=123456789; _gid=GA1.2.3.4");

        assertAll(
                () -> assertThat(cookie.getValue("JSESSIONID")).isEqualTo(Optional.of("123456789")),
                () -> assertThat(cookie.getValue("_gid")).isEqualTo(Optional.of("GA1.2.3.4"))
        );
    }

    @Test
    @DisplayName("빈 문자열로 생성하면 빈 쿠키")
    void createEmptyCookie() {
        RequestCookie cookie = RequestCookie.from("");

        assertThat(cookie).isEqualTo(new RequestCookie(Collections.emptyMap()));
    }
}