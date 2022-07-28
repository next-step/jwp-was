package webserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.domain.Cookie.CookieAttribute;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("쿠키 객체 테스트")
class CookieTest {

    public static final String PATH = "Path";
    public static final String SLASH = "/";
    public static final String VALID_COOKIE_LINE ="keyA=valueA; Max-Age=1000; HttpOnly; Path=/";
    public static final int VALID_COOKIE_SIZE = 3;

    @DisplayName("기본 생성자 테스트")
    @Test
    void createCookie() {
        Cookie cookie = new Cookie();
        assertThat(cookie.getStore()).isEmpty();
    }

    @DisplayName("Map 자료구조 타입을 인수로 쿠키를 생성할 수 있다.")
    @Test
    void createCookieWithMap() {
        Map<CookieAttribute, String> cookieMap = new EnumMap<>(CookieAttribute.class);
        cookieMap.put(CookieAttribute.PATH, SLASH);

        Cookie cookie = new Cookie("keyA", "valueA", cookieMap);

        assertThat(cookie.getAttribute(PATH)).isEqualTo(SLASH);
    }

    @DisplayName("유효한 쿠키 문자열을 인수로 쿠키객체를 생성할 수 있다.")
    @Test
    void createCookieWithValidLine() {
        Cookie cookie = Cookie.from(VALID_COOKIE_LINE);


        assertAll(
                ()-> assertThat(cookie.getStore()).hasSize(VALID_COOKIE_SIZE),
                ()-> assertThat(cookie.getName()).isEqualTo("keyA"),
                ()-> assertThat(cookie.getValue()).isEqualTo("valueA")
        );
    }

    @DisplayName("쿠키에 유효하지 않은 새로운 키/값은 추가할 수 없다.")
    @ParameterizedTest
    @CsvSource(value = {"a,b", "logined,true", "1,2"})
    void addAttributeWithValidPair(String key, String value) {
        Cookie cookie = new Cookie();

        cookie.addAttribute(key, value);

        assertThat(cookie.getAttribute(key)).isNull();
    }

    @DisplayName("쿠키의 속성을 꺼내도 변경할 수 없다.")
    @Test
    void immutableCookieStore() {
        Cookie cookie = Cookie.from(VALID_COOKIE_LINE);

        Map<CookieAttribute, String> store = cookie.getStore();

        assertAll(
                ()-> assertThatThrownBy(()-> store.put(CookieAttribute.MAX_AGE, "newValue"))
                        .isInstanceOf(UnsupportedOperationException.class),

                ()-> assertThatThrownBy(()-> store.remove(CookieAttribute.MAX_AGE))
                        .isInstanceOf(UnsupportedOperationException.class),

                ()-> assertThatThrownBy(store::clear)
                        .isInstanceOf(UnsupportedOperationException.class)
        );
    }

}
