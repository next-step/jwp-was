package webserver.http.cookie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Java6Assertions.assertThat;

class HttpCookiesTest {

    @DisplayName("쿠키를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "a=1",
            "a=1; aa=2",
            "a=asdasd; sadf=34g3; dfsgdfgd=g34342",
    })
    void create(final String rawCookie) {
        // when
        final Cookies httpCookies = HttpCookies.of(rawCookie);

        // then
        assertThat(httpCookies).isNotNull();
    }

    @DisplayName("빈값의 쿠키를 생성하면 빈값이다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            " "
    })
    void empty(final String rawCookie) {
        // when
        final Cookies httpCookies = HttpCookies.of(rawCookie);

        // then
        assertThat(httpCookies.isEmpty()).isTrue();
    }

    @DisplayName("쿠키의 스트링 값을 가져온다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "a",
            "b",
            "c"
    })
    void getString(final String key) {
        // given
        final Cookies httpCookies = HttpCookies.of("a=a; b=b; c=c");

        // when
        final String value = httpCookies.getString(key).get();

        // then
        assertThat(value).isEqualTo(key);
    }

    @DisplayName("쿠키의 Boolean 값을 가져온다.")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void getBoolean(final boolean aBoolean) {
        // given
        final String key = "test";
        final Cookies httpCookies = HttpCookies.of(key + "=" + aBoolean);

        // when
        final boolean value = httpCookies.getBoolean(key);

        // then
        assertThat(value).isEqualTo(aBoolean);
    }

    @DisplayName("쿠키의 값이 없으면 가져올 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "a",
            "b",
            "c"
    })
    void notFound(final String key) {
        // given
        final Cookies httpCookies = HttpCookies.of("a=a; b=b; c=c");
        final String padding = "!!";

        // when
        final boolean exists = httpCookies.getString(key + padding).isPresent();

        // then
        assertThat(exists).isFalse();
    }
}
