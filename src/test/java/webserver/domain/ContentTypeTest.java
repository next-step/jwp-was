package webserver.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class ContentTypeTest {
    final String css = "css";
    final String eot = "eot";
    final String svg = "svg";
    final String ttf = "ttf";
    final String woff = "woff";
    final String woff2 = "woff2";
    final String png = "png";
    final String js = "js";

    @Test
    void getContentTypeTest() {
        assertThat(ContentType.valueOf(css.toUpperCase())).isEqualTo(ContentType.CSS);
        assertThat(ContentType.valueOf(eot.toUpperCase())).isEqualTo(ContentType.EOT);
        assertThat(ContentType.valueOf(svg.toUpperCase())).isEqualTo(ContentType.SVG);
        assertThat(ContentType.valueOf(ttf.toUpperCase())).isEqualTo(ContentType.TTF);
        assertThat(ContentType.valueOf(woff.toUpperCase())).isEqualTo(ContentType.WOFF);
        assertThat(ContentType.valueOf(woff2.toUpperCase())).isEqualTo(ContentType.WOFF2);
        assertThat(ContentType.valueOf(png.toUpperCase())).isEqualTo(ContentType.PNG);
        assertThat(ContentType.valueOf(js.toUpperCase())).isEqualTo(ContentType.JS);
    }

}
