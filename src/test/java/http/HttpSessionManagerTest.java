package http;

import org.apache.catalina.filters.ExpiresFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.HandlebarsTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author KingCjy
 */
public class HttpSessionManagerTest {

    private HttpSessionManager sessionManager;

    @BeforeEach
    public void initSessionManager() {
        this.sessionManager = new HttpSessionManager();
    }

    @Test
    public void createSessionTest() {
        HttpSession httpSession = sessionManager.createSession();

        assertThat(httpSession).isNotNull();
    }

    @Test
    public void createSessionWithIdTest() {
        HttpSession httpSession = sessionManager.createSession("MYID");

        assertThat(httpSession.getId()).isEqualTo("MYID");
    }

    @Test
    public void getSessionTest() {
        sessionManager.createSession("MYID");
        HttpSession httpSession = sessionManager.getSession("MYID");
        assertThat(httpSession).isNotNull();
    }
}
