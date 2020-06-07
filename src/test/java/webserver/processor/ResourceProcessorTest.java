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
class ResourceProcessorTest {
    private static final ResourceProcessor resourceProcessor = new ResourceProcessor();
    private HttpResponse httpResponse;

    @BeforeEach
    void init() throws IOException {
        httpResponse = HttpResponse.init();
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("/js, /images, /fonts, /js 로 시작하는 요청이 resource processor와 매치하는지")
    void isMatch(final String requestLine, final boolean expected) throws IOException {
        HttpRequest httpRequest = HttpRequestGenerator.init(requestLine);

        assertThat(resourceProcessor.isMatch(httpRequest)).isEqualTo(expected);
    }

    private static Stream<Arguments> isMatch() {
        return Stream.of(
                Arguments.of("GET /js/scripts.js HTTP/1.1", true),
                Arguments.of("GET /images/80-text.png HTTP/1.1", true),
                Arguments.of("GET /fonts/glyphicons-halflings-regular.ttf HTTP/1.1", true),
                Arguments.of("GET /css/styles.css HTTP/1.1", true),
                Arguments.of("GET /index.html HTTP/1.1", false)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("읽어온 resource 파일이 예상한 것과 같은지")
    void process(final String resource) throws IOException, URISyntaxException {
        String url = "GET " + resource + " HTTP/1.1";
        HttpRequest httpRequest = HttpRequestGenerator.init(url);
        HttpResponse httpResponse = HttpResponse.init();
        byte[] body = FileIoUtils.loadFileFromClasspath("./static" + resource);

        resourceProcessor.process(httpRequest, httpResponse);
        assertThat(httpResponse.getBody()).isEqualTo(body);
    }

    private static Stream<String> process() {
        return Stream.of(
                "/js/scripts.js",
                "/images/80-text.png",
                "/fonts/glyphicons-halflings-regular.ttf",
                "/css/styles.css"
        );
    }

    @Test
    @DisplayName(".js, .css 등으로 끝나는 요청인데 해당하는 파일이 없는경우")
    void matchButNotExist() throws IOException {
        HttpRequest httpRequest = HttpRequestGenerator.init("GET /not-exist.js HTTP/1.1");

        resourceProcessor.process(httpRequest, httpResponse);

        assertThat(httpResponse.getStatusCode()).isEqualTo(StatusCode.NOT_FOUND);
    }
}