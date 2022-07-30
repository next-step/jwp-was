package webserver.session;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionAttributesTest {

    @DisplayName("특정한 이름으로 객체를 저장할 수 있다")
    @Test
    void set_attribute() {
        final SessionAttributes firstAttributes = new SessionAttributes();
        firstAttributes.setAttribute("name", "value");

        final SessionAttributes secondAttributes = new SessionAttributes();
        secondAttributes.setAttribute("name", "value");

        assertThat(firstAttributes).isEqualTo(secondAttributes);
    }

    @DisplayName("저장된 객체를 조회할 수 있다")
    @Test
    void get_attribute() {
        final SessionAttributes attributes = new SessionAttributes();
        attributes.setAttribute("name", "value");

        final Object actual = attributes.getAttribute("name");

        assertThat(actual).isEqualTo("value");
    }

    @DisplayName("저장된 객체가 없는 경우 null을 반환한다")
    @Test
    void get_attribute_when_not_exist() {
        final SessionAttributes attributes = new SessionAttributes();

        final Object actual = attributes.getAttribute("name");

        assertThat(actual).isNull();
    }


    @DisplayName("저장된 객체를 삭제한다")
    @Test
    void remove_attribute() {
        final SessionAttributes attributes = new SessionAttributes();
        attributes.setAttribute("name", "value");

        attributes.removeAttribute("name");

        final Object actual = attributes.getAttribute("name");

        assertThat(actual).isNull();
    }

    @DisplayName("세션에 저장된 모든 객체를 삭제한다")
    @Test
    void invalid_attribute() {
        final SessionAttributes attributes = new SessionAttributes();
        attributes.setAttribute("name", "value");

        attributes.invalidate();

        final SessionAttributes expected = new SessionAttributes();

        assertThat(attributes).isEqualTo(expected);
    }
}
