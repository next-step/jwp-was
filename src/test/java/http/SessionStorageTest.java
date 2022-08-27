package http;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SessionStorageTest {
    @Test
    @DisplayName("객체가 하나만 생성되는지 확인")
    void singleton() {
        SessionStorage sessionStorage = SessionStorage.getInstance();
        SessionStorage sessionStorage1 = SessionStorage.getInstance();

        assertThat(sessionStorage).isEqualTo(sessionStorage1);
    }

    @Test
    @DisplayName("세션 추가")
    void add() {
        //given
        SessionStorage emptyStorage = SessionStorage.getInstance();
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
        SessionStorage sessionStorage = SessionStorage.getInstance();
        HttpSession emptySession = HttpSession.empty();
        sessionStorage.add(emptySession);

        //when
        sessionStorage.remove(emptySession);
        //then
        assertThat(sessionStorage.find(emptySession.getId())).isNotPresent();
    }

}