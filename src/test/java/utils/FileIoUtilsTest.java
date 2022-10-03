package utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    private static final String VIEW_RESOURCES_PATH = "./templates";

    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath(VIEW_RESOURCES_PATH, "/index.html");
        log.debug("file : {}", new String(body));
    }
}
