package utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        log.debug("file : {}", new String(body));
    }

    @DisplayName("파일의 확장자를 구한다")
    @ParameterizedTest(name = "[{arguments}]")
    @CsvSource({
        "/index.html, html",
        "/index.js, js",
        "/index.css, css",
        "/index, ''"
    })
    void file_extension(final String fileName, final String expected)  {
        String extension = FileIoUtils.getFileExtension(fileName);

        Assertions.assertThat(extension).isEqualTo(expected);
    }

}
