package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import webserver.http.mock.MockHttpRequest;
import webserver.http.mock.MockHttpResponse;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static webserver.provider.ServiceInstanceProvider.getOrderedList;

class HttpProcessorTest {

    @DisplayName("each controller by request path, static resource mapped to handler")
    @ParameterizedTest(name = "path: {0} -> dummy result : {1}")
    @MethodSource(value = "samplePaths")
    void process(String path, String result) {
        HttpProcessor httpProcessor = new HttpProcessor(mockHttpHandlers());
        HttpRequest mockHttpRequest = new MockHttpRequest(path);
        HttpResponse mockHttpResponse = new MockHttpResponse();
        httpProcessor.process(mockHttpRequest, mockHttpResponse);
        assertThat(mockHttpResponse.getCookies().get("dummy")).isEqualTo(result);
    }

    private static Stream<Arguments> samplePaths() {
        return Stream.of(
                Arguments.of("/user/login", "1"),
                Arguments.of("/user/list", "1"),
                Arguments.of("/hello.js", "2"),
                Arguments.of("/test.css", "2")
        );
    }

    private List<HttpHandler> mockHttpHandlers() {
        return getOrderedList(asList(new MockHttpControllerHandler(), new MockHttpStaticHandler()));
    }

    private static class MockHttpControllerHandler extends HttpControllerHandler {
        @Override
        public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
            httpResponse.addCookie("dummy", "1");
        }
    }

    private static class MockHttpStaticHandler extends HttpStaticHandler {
        @Override
        public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
            httpResponse.addCookie("dummy", "2");
        }
    }
}
