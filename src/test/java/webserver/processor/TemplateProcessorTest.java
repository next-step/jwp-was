package webserver.processor;

import http.HttpRequest;
import http.RequestLineParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("html로 끝나는 get 요청을 처리해주는 프로세서")
class TemplateProcessorTest {
    private static final HttpRequest httpRequest = HttpRequest.init(RequestLineParser.parse("GET /some.html HTTP/1.1"));
    private static final TemplateProcessor templateProcessor = new TemplateProcessor();

    @Test
    void isMatch() {
        assertThat(templateProcessor.isMatch(httpRequest)).isTrue();
    }
}