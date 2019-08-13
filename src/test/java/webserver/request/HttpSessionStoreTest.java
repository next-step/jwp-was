package webserver.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created by hspark on 2019-08-14.
 */
class HttpSessionStoreTest {

    @Test
    void test_세션ID기반으로_생성() {
        String expected = "1234";
        HttpSession httpSession = HttpSessionStore.getSession(expected);
        Assertions.assertThat(httpSession.getId()).isEqualTo(expected);
    }

    @Test
    void test_세션_null생성_다시_가져오기() {
        HttpSession httpSession = HttpSessionStore.getSession(null);
        httpSession.setAttribute("test", "test");

        HttpSession actual = HttpSessionStore.getSession(httpSession.getId());
        Assertions.assertThat(httpSession).isEqualTo(actual);
    }

    @Test
    void test_세션_다시_가져오기() {
        HttpSession httpSession = HttpSessionStore.getSession("test");
        httpSession.setAttribute("test", "test");

        HttpSession actual = HttpSessionStore.getSession("test");
        Assertions.assertThat(httpSession).isEqualTo(actual);
    }
}
