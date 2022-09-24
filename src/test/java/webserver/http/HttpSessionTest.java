package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.domain.session.HttpSession;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HttpSessionTest {
    @Test
    @DisplayName("세션을 생성한다.")
    void createSession() {
        // given
        // when
        HttpSession httpSession = new HttpSession();

        // then
        assertThat(httpSession.getId()).isNotNull();
    }

    @Test
    @DisplayName("세션의 값을 추가한다.")
    void addSessionAttribute() {
        // given
        HttpSession httpSession = new HttpSession();

        // when
        httpSession.setAttribute("session", "test");

        // then
        assertThat(httpSession.getId()).isNotNull();
    }

    @Test
    @DisplayName("세션의 값을 가져온다.")
    void findSessionAttribute() {
        // given
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute("session", "test");

        // when
        httpSession.getAttribute("session");

        // then
        assertThat(httpSession.getId()).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 세션의 값을 가져올 경우 Exception 발생.")
    void findSessionAttributeIfNotExistThrowException() {
        // given
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute("session", "test");

        // when
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> httpSession.getAttribute("session2"));

        // then
        assertThat(exception).isNotNull();
    }

    @Test
    @DisplayName("세션의 값을 삭제한다.")
    void deleteSessionAttribute() {
        // given
        String session = "session";
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute(session, "test");

        // when
        httpSession.removeAttribute(session);

        // then
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> httpSession.getAttribute(session));
        assertThat(exception).isNotNull();
    }

    @Test
    @DisplayName("세션의 모든 값을 삭제한다.")
    void deleteAllSessionAttribute() {
        // given
        String session = "session";
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute(session, "test");

        // when
        httpSession.invalidate();

        // then
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> httpSession.getAttribute(session));
        assertThat(exception).isNotNull();
    }

}
