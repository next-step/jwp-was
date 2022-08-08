package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContentTypeTest {

    @Test
    void testIsStaticExtension_WithHtml() {
        assertThat(ContentType.isStaticExtension("html")).isFalse();
    }

    @Test
    void testIsStaticExtension_WithIco() {
        assertThat(ContentType.isStaticExtension("ico")).isFalse();
    }

    @Test
    void testIsStaticExtension_WithCss() {
        assertThat(ContentType.isStaticExtension("css")).isTrue();
    }

}