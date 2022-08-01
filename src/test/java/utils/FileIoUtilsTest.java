package utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        log.debug("file : {}", new String(body));
    }

    @DisplayName("파일이 클래스패스에 있는지 체크할 수 있다.")
    @Test
    void existsFileFromClasspath() throws Exception {
        assertThat(FileIoUtils.existsFileFromClassPath("./templates/index.html")).isTrue();
        assertThat(FileIoUtils.existsFileFromClassPath("./templates/index.css")).isFalse();
    }

}
