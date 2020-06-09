package http.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HeaderFieldTest {

    @Test
    void createHeaderField() {
        final String name = "name";
        final String value = "value";

        HeaderField headerField = new HeaderField(name, value);
    }

    @Test
    void getNameHeaderField() {
        final String name = "name";
        final String value = "value";
        final HeaderField headerField = new HeaderField(name, value);

        final String result = headerField.getName();

        assertThat(result).isEqualTo(name);
    }

    @Test
    void getValueHeaderField() {
        final String name = "name";
        final String value = "value";
        final HeaderField headerField = new HeaderField(name, value);

        final String result = headerField.getValue();

        assertThat(result).isEqualTo(value);
    }

    @Test
    void createHeaderFieldByEnum() {
        final HeaderFieldName nameType = HeaderFieldName.CONTENT_TYPE;
        final String value = "value";
        final HeaderField headerField = new HeaderField(nameType, value);
    }

    @Test
    void createHeaderFieldByEnum1() {
        final HeaderFieldName nameType = HeaderFieldName.CONTENT_TYPE;
        final String value = "value";
        final HeaderField headerField = new HeaderField(nameType, value);

        final String result = headerField.getValue();

        assertThat(result).isEqualTo(value);
    }

}