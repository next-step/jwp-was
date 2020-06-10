package http;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author KingCjy
 */
public class HttpSessionTest {

    @Test
    public void initTest() {
        HttpSessionManager sessionManager = new HttpSessionManager();

        HttpSession httpSession = sessionManager.createSession();
        assertThat(httpSession.getId()).isNotNull();
        assertThat(sessionManager.getSession(httpSession.getId())).isEqualTo(httpSession);
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

        httpSession.invalidate();

        assertThat(httpSession.getAttribute("logined")).isNull();;
        assertThat(httpSession.getAttribute("logined2")).isNull();;
    }
}
