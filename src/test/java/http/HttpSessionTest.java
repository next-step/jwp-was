package http;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author KingCjy
 */
public class HttpSessionTest {

    @Test
    public void initTest() {
        HttpSession httpSession = new HttpSession();
        assertThat(httpSession.getId()).isNotNull();
        assertThat(HttpSessionManager.getSession(httpSession.getId())).isEqualTo(httpSession);
    }

    @Test
    public void attributesTest() {
        HttpSession httpSession = new HttpSession();

        httpSession.addAttribute("logined", true);

        assertThat(httpSession.getAttribute("logined")).isEqualTo(true);
    }
}
