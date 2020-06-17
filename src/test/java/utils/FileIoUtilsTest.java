package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    @DisplayName("File을 잘 가지고 오는지 테스트")
    void loadFileFromClasspath() throws Exception {
        Optional<byte[]> body = FileIoUtils.loadFileFromClasspath("/css/styles.css");
        assertThat(body.isPresent()).isTrue();
        log.debug("file : {}", new String(body.get()));
    }
}
