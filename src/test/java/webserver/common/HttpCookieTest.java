package webserver.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.common.exception.IllegalCookieException;
import webserver.common.exception.IllegalCookieKeyException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpCookieTest {
    @DisplayName("쿠키의 String 을 파싱해서 값을 가져올 수 있어야 한다.")
    @Test
    void parse() {
        HttpCookie httpCookie = HttpCookie.from("userId=javajigi&password=password&name=JaeSung");
        assertAll(
                () -> assertThat(httpCookie.get("userId")).isEqualTo("javajigi"),
                () -> assertThat(httpCookie.get("password")).isEqualTo("password"),
                () -> assertThat(httpCookie.get("name")).isEqualTo("JaeSung")
        );
    }

    @DisplayName("부적절한 Cookie 일 경우, IllegalCookieException 이 발생한다.")
    @Test
    void illegalCookie() {
        assertThatThrownBy(
                () -> HttpCookie.from("=&=")
        ).isInstanceOf(IllegalCookieException.class);
    }

    @DisplayName("키에 대응되는 값이 존재하지 않으면, IllegalCookieKeyException 이 발생한다.")
    @Test
    void illegalQueryStringKey() {
        HttpCookie httpCookie = HttpCookie.from("");
        assertThatThrownBy(
                () -> httpCookie.get("Illegal_Key")
        ).isInstanceOf(IllegalCookieKeyException.class);
    }

    @DisplayName("key 와 value 를 통해 쿠키를 편집할 수 있다.")
    @Test
    void put() {
        HttpCookie httpCookie = HttpCookie.from("a=b");
        httpCookie.put("name", "JaeSung")
                .put("userId", "javajigi");

        assertThat(httpCookie.toString())
                .isEqualTo("a=b&name=JaeSung&userId=javajigi");
    }
}
