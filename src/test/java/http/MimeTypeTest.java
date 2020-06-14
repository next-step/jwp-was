package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MimeTypeTest {
    MimeType sut;

    @Test
    void mimeType_withHavingParameterMap() {
        // given
        sut = MimeType.TEXT_CSS;
        String contentTypeValue = "text/css;charset=utf-8;";

        // when
        String contentTypeValueString = sut.makeContentTypeValue();

        // then
        assertThat(contentTypeValueString).isEqualTo(contentTypeValue);
    }
}