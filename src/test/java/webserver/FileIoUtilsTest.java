package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

public class FileIoUtilsTest {
    private static final Logger loggger = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    @DisplayName("파일 읽기 테스트")
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        loggger.debug("file : {}", new String(body));
    }
}
