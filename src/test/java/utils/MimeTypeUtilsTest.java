package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MimeTypeUtilsTest {

    @DisplayName("html 파일명 mimetype")
    @Test
    public void typeTest(){
        assertThat(MimeTypeUtils.guessContentTypeFromName("/test.html", "text/html"))
                .isEqualTo("text/html");
    }

    @DisplayName("js 파일명 mimetype")
    @Test
    public void typeTest2(){
        assertThat(MimeTypeUtils.guessContentTypeFromName("/main.js", "application/javascript"))
                .isEqualTo("application/javascript");
    }

    @DisplayName("css 파일명 mimetype")
    @Test
    public void typeTest3(){
        assertThat(MimeTypeUtils.guessContentTypeFromName("/main.css", "text/css"))
                .isEqualTo("text/css");
    }
}
