package webserver.http.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.domain.Cookie;
import webserver.http.domain.Cookies;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CookiesTest {

    @DisplayName("of 정적메서드로 Cookies 객체 생성")
    @Test
    void of() {
        Cookies cookies = Cookies.of(
                new Cookie("name1", "value1"),
                new Cookie("name2", "value2")
        );

        assertThat(cookies).usingRecursiveComparison()
                .isEqualTo(
                        new Cookies(Map.of(
                                "name1", new Cookie("name1", "value1", "/"),
                                "name2", new Cookie("name2", "value2", "/")
                        ))
                );
    }

    @DisplayName("새로운 쿠키를 추가한다. 동일한 name의 쿠키 저장시, 새로 추가한 쿠키로 대체된다.")
    @Test
    void addCookie() {
        Cookies cookies = Cookies.of(new Cookie("key1", "value1"));
        cookies.addCookie(new Cookie("key2", "value2"));
        cookies.addCookie(new Cookie("key1", "newValue1"));

        assertThat(cookies).usingRecursiveComparison()
                .isEqualTo(Cookies.of(
                        new Cookie("key1", "newValue1"),
                        new Cookie("key2", "value2")
                ));
    }
}