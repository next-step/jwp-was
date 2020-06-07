package webserver.processor;

import http.HttpRequest;
import http.response.HttpResponse;
import http.StatusCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testutils.FileLoader;
import testutils.HttpRequestGenerator;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("컨트롤러 요청을 모아둔 프로세서 테스트")
class ControllerProcessorTest {
    private static final Logger logger = LoggerFactory.getLogger(ControllerProcessorTest.class);
    private final ControllerProcessor controllerProcessor = new ControllerProcessor();

    @ParameterizedTest
    @MethodSource
    @DisplayName("isMatch 로 controller에 존재하는 요청들이 잘 걸러지는지")
    void isMatch(final HttpRequest httpRequest, final boolean expected) {
        assertThat(controllerProcessor.isMatch(httpRequest)).isEqualTo(expected);
    }

    private static Stream<Arguments> isMatch() throws IOException {
        return Stream.of(
                Arguments.of(HttpRequestGenerator.init("POST /user/login HTTP/1.1"), true),
                Arguments.of(HttpRequestGenerator.init("GET /user/login HTTP/1.1"), true),
                Arguments.of(HttpRequestGenerator.init("GET /user/list HTTP/1.1"), true),
                Arguments.of(HttpRequestGenerator.init("GET /not/exist HTTP/1.1"), false)
        );
    }

    @Test
    @DisplayName("process 결과 확인")
    void process() throws IOException {
        HttpRequest httpRequest = HttpRequestGenerator.init("POST /user/login HTTP/1.1");
        HttpResponse httpResponse = HttpResponse.init();

        assertThat(httpResponse.getStatusCode()).isEqualTo(StatusCode.OK);

        controllerProcessor.process(httpRequest, httpResponse);

        assertThat(httpResponse.getStatusCode()).isEqualTo(StatusCode.REDIRECT);
    }

    @Test
    @DisplayName("render 결과 확인")
    void render() throws IOException {
        HttpRequest httpRequest = HttpRequest.readRawRequest(FileLoader.load("user-list-get-loggedin.txt"));
        HttpResponse httpResponse = HttpResponse.init();

        assertThat(httpResponse.getStatusCode()).isEqualTo(StatusCode.OK);

        controllerProcessor.process(httpRequest, httpResponse);

        assertThat(httpResponse.getStatusCode()).isEqualTo(StatusCode.OK);
        assertThat(httpResponse.getBodyLength()).isNotZero();
        logger.info(new String(httpResponse.getBody()));
    }
}
