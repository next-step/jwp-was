package utils;

import model.ResponseBody;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        final byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        log.debug("file : {}", body.toString());
    }

    @Test
    void css_파일가져오기() throws Exception {
        final byte[] body = FileIoUtils.loadFileFromClasspath("./static/css/styles.css");
        log.debug("length : {}", body.length);
        log.debug("file : {}", new String(body));
    }
}