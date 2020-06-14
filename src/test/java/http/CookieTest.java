package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CookieTest {

    @Test
    @DisplayName("cookie 제대로 생성되는지 테스트")
    void makeCookieTest() {
        Cookie cookie = new Cookie();
        cookie.addCookieValue("logined","test");
        assertEquals(cookie.getCookieValue("logined"), "true");
    }

    @Test
    @DisplayName("cookie value 잘 가져오는지 테스트")
    void getCookieTest() {
        Cookie cookie = new Cookie();
        cookie.addCookieValue("logined","true");
        assertTrue(Boolean.parseBoolean(cookie.getCookieValue("logined")));
    }

}
