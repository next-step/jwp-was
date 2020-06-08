package http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class CookiesTest {
    @DisplayName("Cookies 생성")
    @Test
    void create() {
        //given
        Cookie cookie = new Cookie("JSESSIONID=abc1234", "/", true);
        Cookie cookie2 = new Cookie("JSESSIONID2=abc12345", "/index", false);

        //when
        Cookies cookies = new Cookies(Arrays.asList(cookie, cookie2));

        //then
        assertThat(cookies.getSize()).isEqualTo(2);
        assertThat(cookies.toString())
                .isEqualTo("JSESSIONID=abc1234; Path=/; HttpOnly"
                        + "&"
                        + "JSESSIONID2=abc12345; Path=/index; ");
    }

    @DisplayName("Cookie 추가")
    @Test
    void addCookie(){
        //given
        Cookie cookie = new Cookie("JSESSIONID=abc1234", "/", true);
        Cookie cookie2 = new Cookie("JSESSIONID2=abc12345", "/index", false);
        Cookies cookies = new Cookies(Arrays.asList(cookie));

        //when
        cookies.addCookie(cookie2);

        //then
        assertThat(cookies.getSize()).isEqualTo(2);
        assertThat(cookies.toString())
                .isEqualTo("JSESSIONID=abc1234; Path=/; HttpOnly"
                        + "&"
                        + "JSESSIONID2=abc12345; Path=/index; ");
    }
}
