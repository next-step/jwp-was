package webserver;

import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class FileResponseTest {
    @Test
    void htmlFileResponse() throws Exception {
        String fileName = "/index.html";
        byte[] htmlFile = FileIoUtils.loadFileFromClasspath("./templates/index.html");

        assertThat(FileResponse.getFileResponse(fileName).get().getBody()).isEqualTo(htmlFile);
    }

    @Test
    void cssFileResponse() throws Exception {
        String fileName = "/css/styles.css";
        byte[] htmlFile = FileIoUtils.loadFileFromClasspath("./static/css/styles.css");

        assertThat(FileResponse.getFileResponse(fileName).get().getBody()).isEqualTo(htmlFile);
    }

    @Test
    void notFoundFileResponse() {
        String fileName = "/test.html";

        assertThat(FileResponse.getFileResponse(fileName).get().getBody()).isEqualTo(new byte[0]);
    }

    @Test
    void notFoundSuffixFileResponse() {
        String fileName = "/test.ggg";

        assertThat(FileResponse.getFileResponse(fileName).isPresent()).isEqualTo(false);
    }
}
