package http.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HeaderFieldTest {

    @Test
    @DisplayName("HeaderField를 이름, 값으로 객체를 생성할 수 있다")
    void createHeaderField() {
        final String name = "name";
        final String value = "value";

        new HeaderField(name, value);
    }

    @Test
    @DisplayName("HeaderField에서 이름을 가져올 수 있다")
    void getNameHeaderField() {
        final String name = "name";
        final String value = "value";
        final HeaderField headerField = new HeaderField(name, value);

        final String result = headerField.getName();

        assertThat(result).isEqualTo(name);
    }

    @Test
    @DisplayName("HeaderField에서 값을 가져올 수 있다")
    void getValueHeaderField() {
        final String name = "name";
        final String value = "value";
        final HeaderField headerField = new HeaderField(name, value);

        final String result = headerField.getValue();

        assertThat(result).isEqualTo(value);
    }

    @Test
    @DisplayName("HeaderField에서 이름을 가져올 수 있다")
    void createHeaderFieldByEnum() {
        final HeaderFieldName nameType = HeaderFieldName.CONTENT_TYPE;
        final String value = "value";
        final HeaderField headerField = new HeaderField(nameType, value);

        final String result = headerField.getValue();

        assertThat(result).isEqualTo(value);
    }

}