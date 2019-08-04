package webserver;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.Stream;

/**
 * Created by hspark on 2019-08-05.
 */
class FileResolverTest {
    private static final Logger log = LoggerFactory.getLogger(FileResolverTest.class);
    public FileResolver fileResolver = new FileResolver();

    @ParameterizedTest(name = "httpRequest : {0}")
    @MethodSource("getTestSources")
    void test_file_resolve(HttpRequest httpRequest) throws IOException, URISyntaxException {
        byte[] body = fileResolver.resolve(httpRequest);
        log.debug("file : {}", new String(body));
    }

    private static Stream<Arguments> getTestSources() {
        return Stream.of(
                Arguments.of(HttpRequest.builder().requestLine("GET /css/bootstrap.min.css HTTP/1.1").build()),
                Arguments.of(HttpRequest.builder().requestLine("GET /js/bootstrap.min.js HTTP/1.1").build()),
                Arguments.of(HttpRequest.builder().requestLine("GET /index.html HTTP/1.1").build())
        );
    }
}