package request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CookieTest {

    @Test
    @DisplayName("쿠킥 만들어 값 확인하기")
    void cookieTest() {
        String header = "cookie=1413131; visited=123141341; logined=true";

        Cookie cookie = Cookie.of(header);
        Cookie 비교값 = new Cookie("true");

        assertThat(cookie).isEqualTo(비교값);
    }

}
