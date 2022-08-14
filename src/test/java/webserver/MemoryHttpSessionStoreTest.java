package webserver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpSession;

class MemoryHttpSessionStoreTest {

    @DisplayName("세션 저장소는 새로운 세션을 생성할 수 있다.")
    @Test
    void createSessionTest() {
        // given
        MemoryHttpSessionStore sessionStore = new MemoryHttpSessionStore(() -> "test");

        // when
        HttpSession session = sessionStore.createHttpSession();

        // then
        Assertions.assertThat(session).isNotNull();
        Assertions.assertThat(session).isEqualTo(sessionStore.getSession("test"));
    }

}
