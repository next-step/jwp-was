package webserver.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import utils.FileIoUtils;

class GetIndexHtmlResponseTest {

    @DisplayName("Get /index.html 응답")
    @Test
    void response() throws IOException, URISyntaxException {
        GetIndexHtmlResponse getIndexHtmlResponse = new GetIndexHtmlResponse();
        byte[] actual = getIndexHtmlResponse.response();
        byte[] expect = FileIoUtils.loadFileFromClasspath("./templates/index.html");

        assertThat(actual).hasSameSizeAs(expect);
    }
}