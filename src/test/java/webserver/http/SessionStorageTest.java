package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("세션 저장소")
class SessionStorageTest {

    @Test
    @DisplayName("생성")
    void instance() {
        assertAll(
                () -> assertThatNoException().isThrownBy(SessionStorage::empty),
                () -> assertThatNoException().isThrownBy(() -> SessionStorage.from(Collections.emptyList()))
        );
    }

    @Test
    @DisplayName("세션 조회")
    void find() {
        //given
        HttpSession emptySession = HttpSession.of("id", SessionAttribute.empty());
        SessionStorage sessionStorageWithEmptySession = SessionStorage.from(List.of(emptySession));
        //when, then
        assertThat(sessionStorageWithEmptySession.find("id")).isEqualTo(Optional.of(emptySession));
    }

    @Test
    @DisplayName("세션 추가")
    void add() {
        //given
        SessionStorage emptyStorage = SessionStorage.empty();
        HttpSession emptySession = HttpSession.empty();
        //when
        emptyStorage.add(emptySession);
        //then
        assertThat(emptyStorage.find(emptySession.getId())).isPresent();
    }

    @Test
    @DisplayName("세션 제거")
    void remove() {
        //given
        HttpSession emptySession = HttpSession.empty();
        SessionStorage sessionStorageWithEmptySession = SessionStorage.from(List.of(emptySession));
        //when
        sessionStorageWithEmptySession.remove(emptySession);
        //then
        assertThat(sessionStorageWithEmptySession.find(emptySession.getId())).isNotPresent();
    }
}
