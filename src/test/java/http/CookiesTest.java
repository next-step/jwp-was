package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Cookies 테스트")
class CookiesTest {

    @DisplayName("쿠키 문자열들을 가지고 Cookies 객체를 생성한다.")
    @Test
    void create() {
        Cookies cookies = Cookies.createCookies("bsgid=810aa242-0e22-4471-93f5-48ca2dfef07b; wcs_bt=642c55d33b747:1590654161; logined=true");
        assertThat(cookies).isEqualTo(new Cookies(
                Arrays.asList(new Cookie("bsgid=810aa242-0e22-4471-93f5-48ca2dfef07b"), new Cookie("wcs_bt=642c55d33b747:1590654161"), new Cookie("logined=true"))
        ));
    }

}
