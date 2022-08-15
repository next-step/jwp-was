package webserver.http.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionAttributesTest {

    private static final String name1 = "name1";
    private static final String value1 = "value1";
    private static final String name2 = "name2";
    private static final String value2 = "value2";


    @DisplayName("세션의 속성값을 가져온다.")
    @Test
    public void getAttribute() {
        final SessionAttributes sessionAttributes = new SessionAttributes();
        sessionAttributes.setAttribute(name1, value1);

        final Object attribute = sessionAttributes.getAttribute(name1);

        assertEquals(value1, attribute);
    }

    @DisplayName("존재하지 않는 속성값을 가져오면 예외를 리턴한다.")
    @Test
    public void getNotExistAttribute() {
        final String notExistAttributeName = "n2";
        final SessionAttributes sessionAttributes = new SessionAttributes();
        sessionAttributes.setAttribute(name1, value1);

        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> sessionAttributes.getAttribute(notExistAttributeName));
    }

    @DisplayName("세션의 속성값을 삭제한다.")
    @Test
    public void removeAttribute() {
        final SessionAttributes expected = new SessionAttributes();
        expected.setAttribute(name1, value1);

        final SessionAttributes sessionAttributes = new SessionAttributes();
        sessionAttributes.setAttribute(name1, value1);
        sessionAttributes.setAttribute(name2, value2);
        sessionAttributes.removeAttribute(name2);

        assertEquals(expected, sessionAttributes);
    }

    @DisplayName("세션의 속성값을 모두 삭제한다.")
    @Test
    public void invalidateAttribute() {
        final SessionAttributes expected = new SessionAttributes();

        final SessionAttributes sessionAttributes = new SessionAttributes();
        sessionAttributes.setAttribute(name1, value1);
        sessionAttributes.invalidate();

        assertEquals(expected, sessionAttributes);
    }
}
