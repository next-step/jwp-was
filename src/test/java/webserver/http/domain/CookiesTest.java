package webserver.http.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CookiesTest {

    @DisplayName("새로운 쿠키를 추가한다. 동일한 name의 쿠키 저장시, 새로 추가한 쿠키로 대체된다.")
    @Test
    void addCookie() {
        Cookies cookies = new Cookies(new LinkedHashMap<>());
        cookies.addCookie(new Cookie("key1", "value1"));
        cookies.addCookie(new Cookie("key2", "value2"));
        cookies.addCookie(new Cookie("key1", "newValue1"));

        assertThat(cookies).usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(new Cookies(
                        Map.of(
                                "key1", new Cookie("key1", "newValue1"),
                                "key2", new Cookie("key2", "value2")
                        )
                ));
    }
}