package utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        Optional<byte[]> body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        assertThat(body.isPresent()).isTrue();
    }

    @Test
    void notFoundFile() throws Exception {
        Optional<byte[]> body = FileIoUtils.loadFileFromClasspath("./templates/aa.html");
        assertThat(body.isPresent()).isFalse();
    }
}
