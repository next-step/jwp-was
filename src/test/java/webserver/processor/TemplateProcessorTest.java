package webserver.processor;

import http.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testutils.HttpRequestGenerator;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static http.RawRequestTest.HEADER;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("html로 끝나는 get 요청을 처리해주는 프로세서")
class TemplateProcessorTest {
    private static HttpRequest httpRequest;
    private static final TemplateProcessor templateProcessor = new TemplateProcessor();

    @BeforeAll
    static void init() throws IOException {
        httpRequest = HttpRequestGenerator.init("GET /index.html HTTP/1.1");
    }

    @Test
    @DisplayName(".html로 끝나는 요청이 template processor와 매치하는지")
    void isMatch() {
        assertThat(templateProcessor.isMatch(httpRequest)).isTrue();
    }

    @Test
    @DisplayName("읽어온 html파일이 예상한 것과 같은지")
    void process() throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");

        HttpResponse httpResponse = templateProcessor.process(httpRequest);
        assertThat(httpResponse.getBody()).isEqualTo(body);
    }
}