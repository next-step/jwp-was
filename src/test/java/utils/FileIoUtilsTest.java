package utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        //given
        String filePath = "/index.html";

        //when
        byte[] body = FileIoUtils.loadFileFromClasspath(filePath);
        log.debug("file : {}", new String(body));

        //then
        assertThat(body).contains("<title>SLiPP Java Web Programming</title>".getBytes());
    }
}