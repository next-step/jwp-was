package webserver.processor;

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
}
