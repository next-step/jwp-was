package webserver.http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseBodyTest {

    @DisplayName("비어있는 ResponseBody에 대한 테스트")
    @Test
    void testResponseBody() {
        assertThat(new ResponseBody(new byte[0]))
                .isEqualTo(new ResponseBody(""));
    }

    @DisplayName("index.html 테스트")
    @Test
    void testResponseBodyWithHeaders() throws IOException, URISyntaxException {
        assertThat(new ResponseBody(FileIoUtils.loadFileFromClasspath("./templates/index.html")))
                .isEqualTo(new ResponseBody(Files.readAllBytes(Path.of("/Users/joel.you/git/jwp-was/build/resources/main/templates/index.html"))));
    }
}