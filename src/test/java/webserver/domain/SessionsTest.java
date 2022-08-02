package webserver.domain;

import static org.assertj.core.api.Assertions.assertThat;

import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionsTest {

    private HttpSession sessions1;
    private HttpSession sessions2;
    private HttpSession sessions3;

    @BeforeEach
    void setUp() {
        this.sessions1 = Sessions.INSTANCE.create();
        this.sessions2 = Sessions.INSTANCE.create();
        this.sessions3 = Sessions.INSTANCE.create();
    }

    @Test
    void createSessionTest() {
        HttpSession session = Sessions.INSTANCE.create();

        assertThat(session).isNotNull();
        assertThat(session.getId()).isNotEmpty();
    }

    @DisplayName("기존에 만들어져 저장된 세션은 그대로 가져온다.")
    @Test
    void getSessionTest() {
        HttpSession getSession1 = Sessions.INSTANCE.get(this.sessions1.getId());
        HttpSession getSession2 = Sessions.INSTANCE.get(this.sessions2.getId());
        HttpSession getSession3 = Sessions.INSTANCE.get(this.sessions3.getId());

        assertThat(getSession1).isEqualTo(this.sessions1);
        assertThat(getSession2).isEqualTo(this.sessions2);
        assertThat(getSession3).isEqualTo(this.sessions3);
    }

}
