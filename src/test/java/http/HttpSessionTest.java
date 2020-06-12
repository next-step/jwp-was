package http;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Created by iltaek on 2020/06/12 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class HttpSessionTest {

    @DisplayName("HttpSession이 정상적으로 생성되는지 확인하는 테스트")
    @Test
    void createSessionTest() {
        String id = UUID.randomUUID().toString();
        HttpSession session = new HttpSession(id);
        assertThat(session.getId()).isEqualTo(id);
        assertThat(session).isEqualTo(new HttpSession(id));
    }

    @DisplayName("Session의 attribute를 Set/Get 하는 테스트")
    @ParameterizedTest
    @CsvSource(value = {"a:a", "b:b", "c:c", "key:val"}, delimiter = ':')
    void getAttributeTest(String key, String value) {
        HttpSession session = new HttpSession(UUID.randomUUID().toString());
        session.setAttribute(key, value);
        assertThat(session.getAttribute(key)).isEqualTo(value);
    }

    @DisplayName("Session의 removeAttribute 메서드 테스트")
    @Test
    void removeAttributeTest() {
        HttpSession session = new HttpSession(UUID.randomUUID().toString());
        session.setAttribute("a", "b");
        assertThat(session.getAttribute("a")).isEqualTo("b");

        session.removeAttribute("a");
        assertThat(session.getAttribute("a")).isEqualTo(null);
    }

    @DisplayName("Session의 invalidate 메서드 테스트")
    @Test
    void invalidateAttributeTest() {
        List<String> testValues = new ArrayList<>(Arrays.asList("a", "b", "c"));
        HttpSession session = new HttpSession(UUID.randomUUID().toString());
        for (String testValue : testValues) {
            session.setAttribute(testValue, testValue);
        }
        for (String item : testValues) {
            assertThat(session.getAttribute(item)).isEqualTo(item);
        }

        session.invalidate();
        for (String item : testValues) {
            assertThat(session.getAttribute(item)).isEqualTo(null);
        }
    }
}
