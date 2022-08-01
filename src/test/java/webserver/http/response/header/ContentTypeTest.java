package webserver.http.response.header;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.response.header.ContentType;

class ContentTypeTest {

    @DisplayName("확장자에 따라서 Content-type이 리턴 되어야 한다.")
    @Test
    void contentTypeTest() {
        assertThat(ContentType.response("index.html")).isEqualTo(ContentType.HTML);
        assertThat(ContentType.response("javascript.js")).isEqualTo(ContentType.JS);
        assertThat(ContentType.response("style.css")).isEqualTo(ContentType.CSS);
        assertThat(ContentType.response("fa.ico")).isEqualTo(ContentType.ICO);
        assertThat(ContentType.response("user/idx")).isEqualTo(ContentType.HTML);
    }
}
