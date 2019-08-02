package utils;

import exceptions.ResourceNotFoundException;
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

    @Test
    void loadFileFromClasspathNoResource() throws Exception {
        assertThatThrownBy(() -> FileIoUtils.loadFileFromClasspath("./templates/nofile.html"))
            .isInstanceOf(ResourceNotFoundException.class);

    }
}
