package webserver.http.response;

import org.junit.jupiter.api.Test;
import webserver.http.header.HttpStaticResourceFileExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static webserver.http.header.HttpHeaderConstants.TEXT_CSS_CHARSET_UTF_8;
import static webserver.http.header.HttpHeaderConstants.TEXT_HTML_CHARSET_UTF_8;

class HttpContentTypeHeaderTest {

    @Test
    void of() {
        assertAll(
                () -> assertThat(HttpContentTypeHeader.of(HttpStaticResourceFileExtension.HTML)).isEqualTo(HttpContentTypeHeader.HTML_CONTENT_TYPE),
                () -> assertThat(HttpContentTypeHeader.of(HttpStaticResourceFileExtension.CSS)).isEqualTo(HttpContentTypeHeader.CSS_CONTENT_TYPE),
                () -> assertThat(HttpContentTypeHeader.of(HttpStaticResourceFileExtension.NOTHING)).isEqualTo(HttpContentTypeHeader.NONE_CONTENT_TYPE),
                () -> assertThat(HttpContentTypeHeader.of(HttpStaticResourceFileExtension.NONE)).isEqualTo(HttpContentTypeHeader.NONE_CONTENT_TYPE)
        );
    }

    @Test
    void isNotNoneContentType() {
        assertAll(
                () -> assertThat(HttpContentTypeHeader.HTML_CONTENT_TYPE.isNotNoneContentType()).isTrue(),
                () -> assertThat(HttpContentTypeHeader.CSS_CONTENT_TYPE.isNotNoneContentType()).isTrue(),
                () -> assertThat(HttpContentTypeHeader.NONE_CONTENT_TYPE.isNotNoneContentType()).isFalse()
        );
    }

    @Test
    void getContentType() {
        assertAll(
                () -> assertThat(HttpContentTypeHeader.of(HttpStaticResourceFileExtension.HTML).getContentType()).isEqualTo(TEXT_HTML_CHARSET_UTF_8),
                () -> assertThat(HttpContentTypeHeader.of(HttpStaticResourceFileExtension.CSS).getContentType()).isEqualTo(TEXT_CSS_CHARSET_UTF_8),
                () -> assertThat(HttpContentTypeHeader.of(HttpStaticResourceFileExtension.NONE).getContentType()).isEmpty()
        );
    }
}
