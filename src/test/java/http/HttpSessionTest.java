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

        httpSession.setAttribute("logined", true);
        httpSession.setAttribute("logined2", true);

        httpSession.removeAttribute("logined2");
        assertThat(httpSession.getAttribute("logined")).isEqualTo(true);
        assertThat(httpSession.getAttribute("logined2")).isNull();;
    }

    @Test
    public void invalidateTest() {
        HttpSession httpSession = new HttpSession();

        httpSession.setAttribute("logined", true);
        httpSession.setAttribute("logined2", true);

        httpSession.clear();

        assertThat(httpSession.getAttribute("logined")).isNull();;
        assertThat(httpSession.getAttribute("logined2")).isNull();;
    }
}
