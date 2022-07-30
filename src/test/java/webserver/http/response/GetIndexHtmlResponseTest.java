package webserver.http.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import utils.FileIoUtils;
import webserver.http.response.get.GetIndexHtmlResponse;

class GetIndexHtmlResponseTest {

    @DisplayName("Get /index.html 응답")
    @Test
    void response() throws IOException, URISyntaxException {
        // given
        GetIndexHtmlResponse getIndexHtmlResponse = new GetIndexHtmlResponse();
        byte[] expect = FileIoUtils.loadFileFromClasspath("./templates/index.html");

        // when
        byte[] actual = getIndexHtmlResponse.response("./templates", "/index.html");

        // then
        assertThat(actual).hasSameSizeAs(expect);
    }
}