package http;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created By kjs4395 on 2020-06-10
 */
public class HttpSessionTest {

    private HttpSession session;

    @BeforeEach
    void createSession() {
        session = new HttpSession();
        session.setAttribute("test","test");
    }

    @Test
    @DisplayName("새로운 세션을 만들때마다 세션 id 값이 다른지 테스트")
    void sessionIdNotSameTest() {
        HttpSession firstSession = new HttpSession();
        HttpSession secondSession = new HttpSession();

        assertNotSame(firstSession.getId(), secondSession.getId());
    }

    @Test
    @DisplayName("attribute 저장되는지 테스트")
    void setAttributeTest() {
        session.setAttribute("user",new User("kjs4395","password","강지선","kjs4395@gmail.com"));
    }

    @Test
    @DisplayName("attribute 가져오는지 테스트")
    void getAttributeTest() {
        assertSame(session.getAttribute("test"),"test");
    }

    @Test
    void removeAttributeTest() {
        session.removeAttribute("user");
        assertNull(session.getAttribute("user"));
    }

    @Test
    void invalidateTest() {
        String testAttribute = (String) session.getAttribute("test");
        assertNotNull(testAttribute);
        session.invalidate();
        assertNull(session.getAttribute("test"));
    }
}
