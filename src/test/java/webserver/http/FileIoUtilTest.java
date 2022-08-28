package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class FileIoUtilTest {

    @Test
    @DisplayName("index.html 파일을 읽어 본문을 반환한다.")
    void loadFileFromClasspath() throws Exception {
        // when
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");

        // then
        assertThat(body).isNotEmpty();
    }
}
