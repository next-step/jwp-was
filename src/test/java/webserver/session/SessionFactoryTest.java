package webserver.session;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionFactoryTest {
    HttpSession session;

    @BeforeEach
    void setup() {
        session = new HttpSession();
        session.setAttribute("abc", new User("abc", "pass", "abc", "abc@naver.com"));
        session.setAttribute("cde", new User("cde", "pass", "cde", "cde@naver.com"));
    }

    @Test
    @DisplayName("sessionFactory 값 세팅이 잘 되는지 확인")
    void sessionFactorySetTest() {
        UUID uuid = UUID.randomUUID();

        HttpSessionFactory.makeSession(uuid.toString(), session);

        assertThat(HttpSessionFactory.getSession(uuid.toString())).isEqualTo(session);
    }
}
