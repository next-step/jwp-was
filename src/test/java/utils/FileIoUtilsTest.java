package utils;

import exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @DisplayName("FileIoUtils loadFileFromClasspath 테스트 : 리소스 존재")
    @Test
    void loadTest() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        log.debug("file : {}", new String(body));
    }

    @DisplayName("FileIoUtils loadFileFromClasspath 테스트 : 없는 리소스")
    @Test
    void loadTestException() throws Exception {
        assertThatThrownBy(() -> FileIoUtils.loadFileFromClasspath("./templates/nofile.html"))
            .isInstanceOf(ResourceNotFoundException.class);

    }
}
