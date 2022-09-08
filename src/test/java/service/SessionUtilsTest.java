package service;

import db.DataBase;
import db.SessionStore;
import model.Cookie;
import model.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionUtilsTest {

    @Test
    void 쿠키에_담겨있는_세션정보를_가져온다() {
        final String id = UUID.randomUUID().toString();
        final HttpSession httpSession = new HttpSession(id);
        final Cookie cookie = new Cookie("sessionId", id);
        SessionStore.addSession(httpSession);

        final HttpSession sessionInfo = SessionUtils.getSessionInfo(cookie);

        assertThat(sessionInfo.getId()).isEqualTo(id);
    }

    @Test
    void 쿠키에_세션정보가없으면_생성및_추가한다() {

        final HttpSession sessionInfo = SessionUtils.getSessionInfo(Cookie.empty());

        assertThat(sessionInfo != null).isTrue();
        assertThat(SessionStore.findSession(sessionInfo.getId())).isEqualTo(sessionInfo);
    }
}