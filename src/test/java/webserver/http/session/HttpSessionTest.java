package webserver.http.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {

    private static final String SESSION_ID = "ID";
    private static final String KEY = "KEY";

    private Session session;

    @BeforeEach
    void setUp() {
        session = new HttpSession(SESSION_ID);
    }

    @DisplayName("세션 아이디를 가져온다.")
    @Test
    void getId() {
        // when
        final String sessionId = session.getId();

        // then
        assertThat(sessionId).isEqualTo(SESSION_ID);
    }

    @DisplayName("int 속성을 입력 후 가져온다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 5, 67, 9, 76, 54, 676, 54, 5345, 763524, 364576, 8746, 3524, 56347586})
    void getAttributeInt(final int intValue) {
        // given
        session.setAttribute(KEY, intValue);

        // when
        final Object findAttribute = session.getAttribute(KEY).get();

        // then
        assertThat(intValue).isEqualTo(findAttribute);
    }

    @DisplayName("double 속성을 입력 후 가져온다.")
    @ParameterizedTest
    @ValueSource(doubles = {5436.65, 4.46, 7.4, 56.3734, 36.72624, 736.23, .32, 5.26})
    void getAttributeDouble(final double doubleValue) {
        // given
        session.setAttribute(KEY, doubleValue);

        // when
        final Object findAttribute = session.getAttribute(KEY).get();

        // then
        assertThat(doubleValue).isEqualTo(findAttribute);
    }

    @DisplayName("string 속성을 입력 후 가져온다.")
    @ParameterizedTest
    @ValueSource(strings = {"아아", "으아아아아아", ""})
    void getAttributeString(final String stringValue) {
        // given
        session.setAttribute(KEY, stringValue);

        // when
        final Object findAttribute = session.getAttribute(KEY).get();

        // then
        assertThat(stringValue).isEqualTo(findAttribute);
    }

    @DisplayName("속성이 없으면 가져올 수 없다.")
    @Test
    void getAttributeNotFound() {
        // when
        final boolean notExists = session.getAttribute(KEY).isEmpty();

        // then
        assertThat(notExists).isTrue();
    }

    @DisplayName("속성을 삭제하면 가져올 수 없다.")
    @Test
    void removeAttribute() {
        // given
        session.setAttribute(KEY, "VALUE");

        // when
        final boolean removeBeforeExists = session.getAttribute(KEY).isPresent();
        session.removeAttribute(KEY);
        final boolean removeAfterExists = session.getAttribute(KEY).isPresent();

        // then
        assertThat(removeBeforeExists).isTrue();
        assertThat(removeAfterExists).isFalse();
    }

    @DisplayName("세션을 초기화하면 속성을 가져올 수 없다.")
    @Test
    void invalidate() {
        // given
        session.setAttribute(KEY, "VALUE");

        // when
        final boolean removeBeforeExists = session.getAttribute(KEY).isPresent();
        session.invalidate();
        final boolean removeAfterExists = session.getAttribute(KEY).isPresent();

        // then
        assertThat(removeBeforeExists).isTrue();
        assertThat(removeAfterExists).isFalse();
    }

    @DisplayName("아이디가 같으면 같은 객체이다.")
    @Test
    void equals() {
        // given
        final Session other = new HttpSession(SESSION_ID);
        other.setAttribute("HELLO", "WORLD");

        // when
        final boolean equals = session.equals(other);

        // then
        assertThat(equals).isTrue();
    }
}

