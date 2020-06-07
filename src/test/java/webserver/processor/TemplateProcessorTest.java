package webserver.processor;

import http.HttpRequest;
import http.response.HttpResponse;
import http.StatusCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import testutils.HttpRequestGenerator;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("html로 끝나는 get 요청을 처리해주는 프로세서")
class TemplateProcessorTest {
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;
    private static final TemplateProcessor templateProcessor = new TemplateProcessor();

    @BeforeEach
    void init() throws IOException {
        httpRequest = HttpRequestGenerator.init("GET /index.html HTTP/1.1");
        httpResponse = HttpResponse.init();
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName(".html로 끝나는 요청이 template processor와 매치하는지")
    void isMatch(final HttpRequest httpRequest, final boolean expected) {
        assertThat(templateProcessor.isMatch(httpRequest)).isEqualTo(expected);
    }

    private static Stream<Arguments> isMatch() throws IOException {
        HttpRequest httpRequest = HttpRequestGenerator.init("GET /index.html HTTP/1.1");
        return Stream.of(
                Arguments.of(httpRequest, true),
                Arguments.of(HttpRequestGenerator.init("GET /index.htm HTTP/1.1"), false)
        );
    }

    @Test
    @DisplayName(".html로 끝나는 요청인데 해당하는 파일이 없는경우")

    void matchButNotExist() throws IOException {
        HttpRequest httpRequest = HttpRequestGenerator.init("GET /not-exist.html HTTP/1.1");

        templateProcessor.process(httpRequest, httpResponse);

        assertThat(httpResponse.getStatusCode()).isEqualTo(StatusCode.NOT_FOUND);
    }

    @Test
    @DisplayName("읽어온 html파일이 예상한 것과 같은지")
    void process() throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");

        templateProcessor.process(httpRequest, httpResponse);
        assertThat(httpResponse.getBody()).isEqualTo(body);
    }
}