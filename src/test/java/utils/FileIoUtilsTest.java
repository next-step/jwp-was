package utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.domain.exception.ResourceNotFoundException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() {
        byte[] body = FileIoUtils.loadFileFromClasspath("./static/index.html");
        log.debug("file : {}", new String(body));
    }

    @Test
    void loadFileFromClasspath_fail_when_resource_does_not_exist() {
        assertThatThrownBy(() -> FileIoUtils.loadFileFromClasspath("존재하지않는파일의경로"))
                .isInstanceOf(ResourceNotFoundException.class)
                        .hasMessage("요청하신 리소스가 존재하지 않습니다.");
    }
}
