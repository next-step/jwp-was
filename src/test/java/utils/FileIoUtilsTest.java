package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @DisplayName("Classpath에 있는 파일 불러오기")
    @Test
    void loadFileFromClasspath() throws Exception {
        //given
        String filePath = "./templates/index.html";

        //when
        byte[] body = FileIoUtils.loadFileFromClasspath(filePath);

        //then
        assertThat(body).contains("<title>SLiPP Java Web Programming</title>".getBytes());
    }
}