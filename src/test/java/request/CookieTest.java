package request;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CookieTest {

    @Test
    @DisplayName("쿠킥 만들어 값 확인하기")
    void cookieTest() {
        String header = "visited=visited; logined=true";

        RequestCookie cookie = RequestCookie.of(header);
        Map<String, String> cookies = new HashMap<>();
        cookies.put("logined", "true");
        cookies.put("visited", "visited");
        RequestCookie 비교값 = new RequestCookie(cookies);

        assertThat(cookie).isEqualTo(비교값);
    }

}
