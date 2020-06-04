package webserver.processor;

import http.HttpRequest;
import http.HttpResponse;
import http.RequestLineParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("html로 끝나는 get 요청을 처리해주는 프로세서")
class TemplateProcessorTest {
    private static final HttpRequest httpRequest = HttpRequest.init(RequestLineParser.parse("GET /index.html HTTP/1.1"));
    private static final TemplateProcessor templateProcessor = new TemplateProcessor();

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