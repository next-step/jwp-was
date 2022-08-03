package webserver.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpSessionTest {

    public static final String TEST_SESSION_ID_1 = "testSessionId1";
    public static final String TEST_SESSION_ID_2 = "testSessionId2";
    public static final String TEST_SESSION_ID_3 = "testSessionId3";
    public static final String STRING_VALUE_1 = "String value1";
    public static final String STRING_VALUE_2 = "String value2";
    public static final String STRING_VALUE_3 = "String value3";

    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        this.httpSession = new HttpSession();

        this.httpSession.setAttribute(TEST_SESSION_ID_1, STRING_VALUE_1);
        this.httpSession.setAttribute(TEST_SESSION_ID_2, STRING_VALUE_2);
        this.httpSession.setAttribute(TEST_SESSION_ID_3, STRING_VALUE_3);
    }

    @Test
    void createIdTest() {
        HttpSession httpSession = new HttpSession();

        String id = httpSession.getId();
        assertThat(id).isNotEmpty();
    }

    @DisplayName("세션에서 name-object 로 저장된 값을 정상 확인한다.")
    @Test
    void setGetAttributeTest() {
        String testSessionId = (String) this.httpSession.getAttribute(TEST_SESSION_ID_2);

        assertThat(testSessionId).isEqualTo(STRING_VALUE_2);
    }

    @DisplayName("세션에서 저장되지 않은 값 가져오면 null")
    @Test
    void getAttributeNullTest() {
        String testSessionId = (String) this.httpSession.getAttribute("invalid name");

        assertThat(testSessionId).isNull();
    }

    @Test
    void removeAttributeTest() {
        Object attribute = this.httpSession.getAttribute(TEST_SESSION_ID_3);
        assertThat(attribute).isNotNull();

        this.httpSession.removeAttribute(TEST_SESSION_ID_3);
        attribute = this.httpSession.getAttribute(TEST_SESSION_ID_3);

        assertThat(attribute).isNull();
    }

    @DisplayName("세션 저장값 전체 삭제 기능")
    @Test
    void invalidateTest() {
        Object attribute = this.httpSession.getAttribute(TEST_SESSION_ID_1);
        assertThat(attribute).isNotNull();
        attribute = this.httpSession.getAttribute(TEST_SESSION_ID_2);
        assertThat(attribute).isNotNull();
        attribute = this.httpSession.getAttribute(TEST_SESSION_ID_3);
        assertThat(attribute).isNotNull();

        this.httpSession.invalidate();

        attribute = this.httpSession.getAttribute(TEST_SESSION_ID_1);
        assertThat(attribute).isNull();
        attribute = this.httpSession.getAttribute(TEST_SESSION_ID_2);
        assertThat(attribute).isNull();
        attribute = this.httpSession.getAttribute(TEST_SESSION_ID_3);
        assertThat(attribute).isNull();
    }

}
