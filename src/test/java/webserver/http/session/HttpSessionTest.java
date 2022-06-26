package webserver.http.session;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {

    @Test
    void attribute를_지정하고_가져올_수_있다() {
        // given
        final HttpSession session = new HttpSession("id");

        // when
        session.setAttribute("one", 1);

        // then
        assertThat(session.getAttributeOrNull("one")).isEqualTo(1);
    }

    @Test
    void 존재하지_않는_attribute를_조회할_때는_null_리턴() {
        // given
        final HttpSession session = new HttpSession("id");
        session.setAttribute("one", 1);

        // when & then
        assertThat(session.getAttributeOrNull("two")).isNull();
    }

    @Test
    void attribute를_제거할_수_있다() {
        // given
        final HttpSession session = new HttpSession("id");
        session.setAttribute("one", 1);

        // when
        session.removeAttribute("one");

        // then
        assertThat(session.getAttributeOrNull("one")).isNull();
    }

    @Test
    void 세션정보를_제거한다() {
        // given
        final HttpSession session = HttpSessionManager.createSession();
        session.setAttribute("one", 1);

        // when
        session.invalidate();

        // then
        assertThat(session.getAttributeOrNull("one")).isNull();
        assertThat(HttpSessionManager.getSessionOrNull(session.getId())).isNull();
    }
}
