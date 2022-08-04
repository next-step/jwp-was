package utils;

import exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        log.debug("file : {}", new String(body));
    }

    @DisplayName("리소스를 찾을 수 없으면 예외 발생")
    @Test
    void notFoundResource() {
        assertThatThrownBy( () -> FileIoUtils.loadFileFromClasspath("/a.html"))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
