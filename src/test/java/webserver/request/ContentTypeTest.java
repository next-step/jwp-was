package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ContentTypeTest {

    @DisplayName("확장자에 따라 Content-Type을 찾는다")
    @ParameterizedTest
    @CsvSource({
        "html, text/html",
        "js, application/javascript",
        "css, text/css",
        "'', text/plain"
    })
    void content_type_by_extension(final String fileName, final String expected) {
        String contentType = ContentType.of(fileName).getContentType();

        assertThat(contentType).isEqualTo(expected);
    }

}
