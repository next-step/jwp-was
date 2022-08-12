package webserver.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@DisplayName("HttpSession 저장소 테스트")
class HttpSessionStorageTest {

    @DisplayName("HttpSessionStorage 생성")
    @Test
    void create() {
        assertThatNoException().isThrownBy(
                () -> new HttpSessionStorage(Collections.emptyMap()));
    }

    @DisplayName("세션 조회시에")
    @Nested
    class Get_session {

        @DisplayName("세션이 생성되어 있지 않으면")
        @Nested
        class Session_not_exist {

            @DisplayName("세션을 생성한다.")
            @Test
            void createSession() {
                HttpSession session = HttpSessionStorage.getSession("id");

                HttpSession targetSession = HttpSessionStorage.getSession("id");

                assertThat(targetSession).isEqualTo(session);
            }
        }

        @DisplayName("세션이 생성되어 있다면")
        @Nested
        class Session_exist {

            @DisplayName("세션을 반환한다.")
            @Test
            void returnSession() {
                HttpSessionStorage sessionStorage = new HttpSessionStorage(Collections.emptyMap());
                HttpSession session = new HttpSession("key");
                sessionStorage.addSession(session);

                HttpSession targetSession = HttpSessionStorage.getSession("key");

                assertThat(targetSession).isEqualTo(session);
            }
        }
    }

    @DisplayName("세션 제거")
    @Test
    void add() {
        HttpSession session = new HttpSession("id");
        HttpSessionStorage sessionStorage = new HttpSessionStorage(Map.of(session.getId(), session));

        sessionStorage.remove("id");

        assertThat(HttpSessionStorage.getSession(session.getId())).isNotEqualTo(session);
    }
}
