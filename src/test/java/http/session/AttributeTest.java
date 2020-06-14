package http.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AttributeTest {
    private Attribute attribute;

    @BeforeEach
    void setUp() {
        attribute = new Attribute();
    }

    @Test
    @DisplayName("생성자 테스트")
    void createByConstructor() {
        // give
        Attribute expected = new Attribute();
        // when
        boolean same = attribute.equals(expected);
        // then
        assertThat(same).isTrue();
    }

    @Test
    @DisplayName("값 가져오기")
    void getValue() {
        // give
        attribute.setAttribute("name", "seongju");
        String actual = (String) attribute.getAttribute("name");
        String expected = "seongju";
        // when
        boolean same = actual.equals(expected);
        // then
        assertThat(same).isTrue();
    }

    @Test
    @DisplayName("값 삭제")
    void removeValue() {
        // give
        attribute.setAttribute("name", "seongju");
        attribute.removeAttribute("name");
        // when
        boolean same = attribute.equals(new Attribute());
        // then
        assertThat(same).isTrue();
    }

    @Test
    @DisplayName("모든 값 초기화")
    void invalidate() {
        // give
        attribute.setAttribute("name", "seongju");
        attribute.setAttribute("name2", "seongju2");
        attribute.setAttribute("name3", "seongju3");
        attribute.invalidate();
        // when
        boolean same = attribute.equals(new Attribute());
        // then
        assertThat(same).isTrue();
    }
}
