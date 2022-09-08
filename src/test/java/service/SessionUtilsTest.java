package service;

import db.SessionStore;
import model.Cookie;
import model.http.HttpSession;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionUtilsTest {

    @Test
    void 쿠키에_담겨있는_세션정보를_가져온다() {
        final HttpSession httpSession = new HttpSession();
        final Cookie cookie = new Cookie("sessionId", httpSession.getId());
        SessionStore.addSession(httpSession);

        final HttpSession sessionInfo = SessionUtils.getSessionInfo(cookie);

        assertThat(sessionInfo.getId()).isEqualTo(httpSession.getId());
    }

    @Test
    void 쿠키에_세션정보가없으면_생성및_추가한다() {

        final HttpSession sessionInfo = SessionUtils.getSessionInfo(Cookie.empty());

        assertThat(sessionInfo != null).isTrue();
        assertThat(SessionStore.findSession(sessionInfo.getId())).isEqualTo(sessionInfo);
    }
}