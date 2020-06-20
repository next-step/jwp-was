package http.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpCookieTest {

    @DisplayName("등호로 연결된 이름과 값의 쌍, 세미콜론으로 연결된 개별 쌍을 파싱하여 쿠키 객 생성")
    @Test
    void test_getCookie() {
        // given
        // when
        HttpCookie cookie = new HttpCookie("JSESSIONID=870be194-0ef2-4dae-99c7-227b6811238c; csrftoken=u32t4o3tb3gg43; _gat=1;");
        // then
        assertAll(
                () -> assertThat(cookie.size()).isEqualTo(3),
                () -> assertThat(cookie.get("JSESSIONID")).isEqualTo("870be194-0ef2-4dae-99c7-227b6811238c"),
                () -> assertThat(cookie.get("csrftoken")).isEqualTo("u32t4o3tb3gg43"),
                () -> assertThat(cookie.get("_gat")).isEqualTo("1")
        );
    }
}