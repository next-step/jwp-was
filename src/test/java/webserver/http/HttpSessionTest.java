package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HttpSessionTest {
    private static final Logger logger = LoggerFactory.getLogger(HttpSessionTest.class);

    private HttpSession httpSession;
    private String attributeName;
    private String attributeValue;

    @BeforeEach
    void setUp() {
        attributeName = "name";
        attributeValue = "dave";
        String sessionId = HttpSessionGenerator.generate();
        httpSession = new HttpSession(sessionId);
    }

    @Test
    void sessionId() {
        String sessionId = httpSession.getId();
        logger.debug(sessionId);

        assertThat(sessionId).isNotBlank();
    }

    @Test
    void setAndGetAttribute() {
        httpSession.setAttribute(attributeName, attributeValue);

        assertThat(httpSession.getAttribute(attributeName)).isEqualTo(attributeValue);
    }

    @Test
    void removeAttribute() {
        httpSession.setAttribute(attributeName, attributeValue);
        httpSession.removeAttribute(attributeName);

        assertThat(httpSession.getAttribute(attributeName)).isEqualTo(null);
    }

    @Test
    void invalidate() {
        String errorMessage = "세션이 이미 무효화 되었습니다.";
        httpSession.setAttribute(attributeName, attributeValue);

        httpSession.invalidate();
        Exception thrown = assertThrows(IllegalStateException.class, () -> httpSession.getAttribute(attributeName));
        assertThat(thrown.getMessage()).isEqualTo(errorMessage);
    }
}
