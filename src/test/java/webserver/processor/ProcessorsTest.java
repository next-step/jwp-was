package webserver.processor;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.StatusCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import testutils.HttpRequestGenerator;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("프로세서 종합 테스트")
class ProcessorsTest {
    private static final Processors processors = new Processors();

    @ParameterizedTest
    @MethodSource
    @DisplayName("매치하지 하는 경우와 하지 않는 경우 Status Code 체크")
    void match(final HttpRequest request, final StatusCode expected) throws IOException {
        HttpResponse httpResponse = HttpResponse.init();

        processors.process(request, httpResponse);

        assertThat(httpResponse.getStatusCode()).isEqualTo(expected);
    }

    private static Stream<Arguments> match() throws IOException {
        return Stream.of(
                Arguments.of(HttpRequestGenerator.init("GET /index.html HTTP/1.1"), StatusCode.OK),
                Arguments.of(HttpRequestGenerator.init("GET /js/scripts.js HTTP/1.1"), StatusCode.OK),
                Arguments.of(HttpRequestGenerator.init("GET /images/80-text.png HTTP/1.1"), StatusCode.OK),
                Arguments.of(HttpRequestGenerator.init("GET /fonts/glyphicons-halflings-regular.ttf HTTP/1.1"), StatusCode.OK),
                Arguments.of(HttpRequestGenerator.init("GET /css/styles.css HTTP/1.1"), StatusCode.OK),
                Arguments.of(HttpRequestGenerator.init("GET /not-exist.html HTTP/1.1"), StatusCode.NOT_FOUND)
        );
    }
}
