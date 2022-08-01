package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("요청 쿠키")
class RequestCookieTest {

    @Test
    @DisplayName("문자열 리스트로 생성")
    void instance() {
        assertThatNoException().isThrownBy(() -> RequestCookie.from("JSESSIONID=123456789; _gid=GA1.2.3.4"));
    }

    @DisplayName("빈 문자열로 생성하면 빈 쿠키")
    @ParameterizedTest
    @NullAndEmptySource
    void instance_emptyString_empty(String cookie) {
        assertThat(RequestCookie.from(cookie)).isEqualTo(RequestCookie.empty());
    }

    @Test
    @DisplayName("값 조회")
    void value() {
        //given
        RequestCookie cookie = RequestCookie.from("JSESSIONID=123456789; _gid=GA1.2.3.4");
        //when, then
        assertAll(
                () -> assertThat(cookie.value("JSESSIONID")).isEqualTo(Optional.of("123456789")),
                () -> assertThat(cookie.value("_gid")).isEqualTo(Optional.of("GA1.2.3.4"))
        );
    }
}
