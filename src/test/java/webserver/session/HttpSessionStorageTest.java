package webserver.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("HttpSession 저장소 테스트")
class HttpSessionStorageTest {

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
                HttpSession session = new HttpSession("key");
                HttpSessionStorage.addSession(session);

                HttpSession targetSession = HttpSessionStorage.getSession("key");

                assertThat(targetSession).isEqualTo(session);
            }
        }
    }

    @DisplayName("세션 제거")
    @Test
    void add() {
        HttpSession session = new HttpSession("id");
        HttpSessionStorage.addSession(session);

        HttpSessionStorage.remove("id");

        assertThat(HttpSessionStorage.getSession(session.getId())).isNotEqualTo(session);
    }

    @DisplayName("세션 저장소 비우기")
    @Test
    void invalidate() {
        HttpSession sessionA = new HttpSession("idA");
        HttpSession sessionB = new HttpSession("idB");
        HttpSessionStorage.addSession(sessionA);
        HttpSessionStorage.addSession(sessionB);

        HttpSessionStorage.invalidate();

        assertAll(
                () -> assertThat(HttpSessionStorage.getSession(sessionA.getId())).isNotEqualTo(sessionA),
                () -> assertThat(HttpSessionStorage.getSession(sessionB.getId())).isNotEqualTo(sessionB)
        );
    }
}
