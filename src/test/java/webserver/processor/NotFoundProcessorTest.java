package webserver.processor;

import http.HttpRequest;
import http.HttpResponse;
import http.StatusCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testutils.HttpRequestGenerator;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Not found 상태를 리턴하기 위한 프로세서")
class NotFoundProcessorTest {
    private final NotFoundProcessor notFoundProcessor = new NotFoundProcessor();

    @Test
    @DisplayName("isMatch는 항상 true를 리턴한다.")
    void isMatch() throws IOException {
        assertThat(
                notFoundProcessor.isMatch(HttpRequestGenerator.init("GET somepath HTTP/1.1"))
        ).isTrue();
    }

    @Test
    @DisplayName("process 를 통과하면 항상 response의 statuscode 값은 404이다")
    void process() throws IOException {
        HttpRequest httpRequest = HttpRequestGenerator.init("GET somepath HTTP/1.1");
        HttpResponse httpResponse = HttpResponse.init();

        notFoundProcessor.process(httpRequest, httpResponse);

        assertThat(httpResponse.getStatusCode()).isEqualTo(StatusCode.NOT_FOUND);
    }
}
