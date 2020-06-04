package webserver.processor;


import http.HttpRequest;
import http.HttpResponse;
import http.RequestLineParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("html로 끝나는 get 요청을 처리해주는 프로세서")
class ResourceProcessorTest {
    private static final ResourceProcessor resourceProcessor = new ResourceProcessor();

    @ParameterizedTest
    @MethodSource
    @DisplayName("/js, /images, /fonts, /js 로 시작하는 요청이 resource processor와 매치하는지")
    void isMatch(final String requestLine, final boolean expected) {
        HttpRequest httpRequest = HttpRequest.init(RequestLineParser.parse(requestLine));

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

    @Test
    @DisplayName("읽어온 html파일이 예상한 것과 같은지")
    void process() throws IOException, URISyntaxException {
/*
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");

        HttpResponse httpResponse = resourceProcessor.process(httpRequest);
        assertThat(httpResponse.getBody()).isEqualTo(body);
*/
    }
}