package webserver.session;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpSessionIdHolderTest {

    @DisplayName("세션 컨텍스트에 세션이 없다면 새로운 세션을 생성하고 세션 ID를 Holder에 저장한다")
    @Test
    void generate_session() {
        // given
        HttpSessionContext.add(new HttpSession(() -> "12345"));

        final String newSessionId = "ABCDEFG";
        final HttpSessionIdHolder httpSessionIdHolder = new HttpSessionIdHolder(() -> newSessionId);

        // when
        httpSessionIdHolder.generate(newSessionId);

        // then
        final String actual = httpSessionIdHolder.getSessionId();

        assertThat(actual).isEqualTo(newSessionId);
        assertThat(HttpSessionContext.get(newSessionId)).isNotNull();
    }

    @DisplayName("세션 컨텍스트에 세션이 있다면 세션을 생성하지 않고 기존 세션의 ID를 Holder에 저장한다")
    @Test
    void session_already_exists() {
        // given
        final HttpSession session = new HttpSession(() -> "12345");
        HttpSessionContext.add(session);

        final HttpSessionIdHolder httpSessionIdHolder = new HttpSessionIdHolder(new UUIDSessionIdGenerator());

        // when
        httpSessionIdHolder.generate(session.getId());

        // then
        final String actual = httpSessionIdHolder.getSessionId();

        assertThat(actual).isEqualTo(session.getId());
        assertThat(HttpSessionContext.get(session.getId())).isEqualTo(session);
    }

    @DisplayName("Holder의 세션 ID를 삭제한다")
    @Test
    void delete_session_id__holder() {
        HttpSessionContext.add(new HttpSession(() -> "12345"));
        final HttpSessionIdHolder httpSessionIdHolder = new HttpSessionIdHolder(() -> "12345");

        httpSessionIdHolder.generate("12345");

        httpSessionIdHolder.invalidate();

        assertThat(httpSessionIdHolder.getSessionId()).isNull();
    }
}
