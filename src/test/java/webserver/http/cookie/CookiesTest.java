package webserver.http.cookie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Java6Assertions.assertThat;

class CookiesTest {

    @DisplayName("쿠키를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "a=1",
            "a=1; aa=2",
            "a=asdasd; sadf=34g3; dfsgdfgd=g34342",
    })
    void create(final String rawCookie) {
        // when
        final Cookies cookies = Cookies.of(rawCookie);

        // then
        assertThat(cookies).isNotNull();
    }

    @DisplayName("빈값의 쿠키를 생성하면 빈값이다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            " "
    })
    void empty(final String rawCookie) {
        // when
        final Cookies cookies = Cookies.of(rawCookie);

        // then
        assertThat(cookies.isEmpty()).isTrue();
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
        final Cookies cookies = Cookies.of("a=a; b=b; c=c");

        // when
        final String value = cookies.getString(key);

        // then
        assertThat(value).isEqualTo(key);
    }

    @DisplayName("쿠키의 Boolean 값을 가져온다.")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void getBoolean(final boolean aBoolean) {
        // given
        final String key = "test";
        final Cookies cookies = Cookies.of(key + "=" + aBoolean);

        // when
        final boolean value = cookies.getBoolean(key);

        // then
        assertThat(value).isEqualTo(aBoolean);
    }

    @DisplayName("쿠키의 값이 없으면 null을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "a",
            "b",
            "c"
    })
    void notFound(final String key) {
        // given
        final Cookies cookies = Cookies.of("a=a; b=b; c=c");
        final String padding = "!!";

        // when
        final String value = cookies.getString(key + padding);

        // then
        assertThat(value).isNull();
    }
}
