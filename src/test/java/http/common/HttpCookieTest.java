package http.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpCookieTest {

    @DisplayName("쿠키 생성 시 인자가 null이거나 빈 문자열이면 IllegalArgumentException 발생")
    @Test
    void test_create() {
        // given
        // when
        HttpCookie byNull = HttpCookie.from(null);
        HttpCookie byEmptyString = HttpCookie.from("");
        // then
        assertAll(
                () -> assertThat(byNull.size()).isEqualTo(0),
                () -> assertThat(byEmptyString.size()).isEqualTo(0)
        );
    }

    @DisplayName("등호로 연결된 이름과 값의 쌍, 세미콜론으로 연결된 개별 쌍을 파싱하여 쿠키 객체 생성")
    @Test
    void test_getCookie() {
        // given
        // when
        HttpCookie cookie = HttpCookie.from("JSESSIONID=870be194-0ef2-4dae-99c7-227b6811238c; csrftoken=u32t4o3tb3gg43; _gat=1;");
        // then
        assertAll(
                () -> assertThat(cookie.size()).isEqualTo(3),
                () -> assertThat(cookie.getCookieValueBy("JSESSIONID")).isEqualTo("870be194-0ef2-4dae-99c7-227b6811238c"),
                () -> assertThat(cookie.getCookieValueBy("csrftoken")).isEqualTo("u32t4o3tb3gg43"),
                () -> assertThat(cookie.getCookieValueBy("_gat")).isEqualTo("1")
        );
    }


}