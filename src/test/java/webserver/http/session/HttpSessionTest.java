package webserver.http.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpSessionTest {

    private static final String name1 = "name1";
    private static final String value1 = "value1";
    private static final String name2 = "name2";
    private static final String value2 = "value2";

    private static final Logger logger = LoggerFactory.getLogger(HttpSessionTest.class);

    @DisplayName("세션을 생성한다.")
    @Test
    public void createHttpSession() {
        final HttpSession httpSession = new HttpSession();

        logger.debug(httpSession.getId());
    }

    @DisplayName("속성값을 가져온다.")
    @Test
    public void getHttpSessionAttribute() {
        final HttpSession httpSession = new HttpSession();
        httpSession.setAttribute(name1, value1);

        final Object attribute = httpSession.getAttribute(name1);

        assertEquals(value1, attribute);
    }

    @DisplayName("존재하지 않는 속성값을 가져오면 예외를 리턴한다.")
    @Test
    public void getNotExistHttpAttribute() {
        final String notExistAttributeName = "n2";
        final HttpSession httpSession = new HttpSession();
        httpSession.setAttribute(name1, value1);

        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> httpSession.getAttribute(notExistAttributeName));
    }

    @DisplayName("세션의 속성값을 삭제한다.")
    @Test
    public void removeAttribute() {
        final HttpSession httpSession = new HttpSession();
        httpSession.setAttribute(name1, value1);
        httpSession.removeAttribute(name1);

        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> httpSession.getAttribute(name1));
    }

    @DisplayName("세션의 속성값을 모두 삭제한다.")
    @Test
    public void invalidateAttribute() {
        final HttpSession httpSession = new HttpSession();
        httpSession.setAttribute(name1, value1);
        httpSession.invalidate();

        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> httpSession.getAttribute(name1));
    }
}
