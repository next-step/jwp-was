package webserver.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DefaultHttpSessionTest {

    private SessionManager sessionManager;

    @BeforeEach
    void setUp() {
        sessionManager = SessionManager.getInstance();
    }

    @DisplayName("기본 생성자를 호출하면 UUID 기반의 아이디를 가진 세션이 생성된다.")
    @Test
    void createNoArgsSession() {
        DefaultHttpSession session = new DefaultHttpSession();

        assertThat(session.getId()).isNotNull();
    }

    @DisplayName("원하는 방식의 특정 아이디로 세션 객체를 만들 수 있다.")
    @Test
    void createWithParams() {
        DefaultHttpSession session = new DefaultHttpSession("test", Collections.singletonMap("key", "value"));

        assertAll(
                ()-> assertThat(session.getId()).isEqualTo("test"),
                ()-> assertThat(session.getAttribute("key")).isEqualTo("value")
        );
    }

    @DisplayName("정적 팩토리를 이용해 sessionManager를 구독하는 세션객체를 만들 수 있다.")
    @Test
    void createAndSubscribe() {
        HttpSession session = DefaultHttpSession.newInstance(sessionManager);

        assertThat(sessionManager.findBySessionId(session.getId())).isEqualTo(session);
    }

    @DisplayName("invalidate 메서드를 통해 세션정보를 초기화하고 세션 매니저와 연결을 해지할 수 있다.")
    @Test
    void invalidate() {
        HttpSession session = DefaultHttpSession.newInstance(sessionManager);

        session.invalidate();

        assertThat(sessionManager.findBySessionId(session.getId())).isNull();
    }

    @DisplayName("세션 정보에 속성을 추가할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideSessionAttributePair")
    void addAttributeWithValidData(String key, Object value) {
        HttpSession session = DefaultHttpSession.newInstance(sessionManager);

        session.setAttribute(key, value);

        assertThat(session.getAttribute(key)).isEqualTo(value);


    }

    public static Stream<Arguments> provideSessionAttributePair() {
        return Stream.of(
                Arguments.of("keyA", "valueA"),
                Arguments.of("keyB", 997),
                Arguments.of("keyC", ContentType.HTML)
        );
    }

    @DisplayName("세션 정보에 동일한 키로 다른 값을 전달하면 덮어씌워진다. ")
    @Test
    void addAttributeWithDuplicateKey() {
        HttpSession session = DefaultHttpSession.newInstance(sessionManager);
        session.setAttribute("A", "A");
        session.setAttribute("A", "B");

        assertThat(session.getAttribute("A")).isNotEqualTo("A");
        assertThat(session.getAttribute("A")).isEqualTo("B");

    }

    @DisplayName("세션 정보에서 유효한 키를 전달하면 속성을 제거할 수 있다.")
    @Test
    void removeAttribute() {
        HttpSession session = DefaultHttpSession.newInstance(sessionManager);
        session.setAttribute("keyA", "valueA");

        session.removeAttribute("keyA");

        assertThat(session.getAttribute("keyA")).isNull();
    }

    @DisplayName("세션 정보에서 유효하지 않은 키를 전달하면 아무일도 일어나지 않는다.")
    @Test
    void removeAttributeWithInvalidKey() {
        HttpSession session = DefaultHttpSession.newInstance(sessionManager);
        session.setAttribute("keyA", "valueA");

        assertDoesNotThrow(()-> session.removeAttribute("keyB"));
        assertThat(session.getAttribute("keyA")).isEqualTo("valueA");
    }

}
