package webserver.http;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {

    @Test
    void 세션생성_테스트() {
        UUID uuid = UUID.randomUUID();
        HttpSession session = new HttpSession(uuid.toString());

        assertThat(session.getId()).isEqualTo(uuid.toString());
    }

    @Test
    void 세션저장_조회_테스트() {
        String userName = "hjjang87";
        String userEmail = "dacapolife87@gmail.com";

        UUID uuid = UUID.randomUUID();
        HttpSession session = new HttpSession(uuid.toString());

        session.setAttribute("userName", userName);
        session.setAttribute("userEmail", userEmail);


        assertThat(session.getId()).isEqualTo(uuid.toString());
        assertThat(session.getAttribute("userName")).isEqualTo(userName);
        assertThat(session.getAttribute("userEmail")).isEqualTo(userEmail);
    }

    @Test
    void 세션_무효화_테스트() {
        String userName = "hjjang87";

        UUID uuid = UUID.randomUUID();
        HttpSession session = new HttpSession(uuid.toString());

        session.setAttribute("userName", userName);
        session.invalidate();

        assertThat(session.getAttribute("userName")).isNull();
    }
}
