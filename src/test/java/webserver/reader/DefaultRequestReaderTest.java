package webserver.reader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("리퀘스트를 읽어서 문자열로 반환하는 클래스")
class DefaultRequestReaderTest {
    private static final String REQUEST_HEADER_STRING = "GET /index.html HTTP/1.1";

    @Test
    @DisplayName("요청한 raw request 와 request reader 가 반환하는 문자열이 같은지 확인")
    void readStream() throws IOException {
        InputStream testInputStream = new ByteArrayInputStream(REQUEST_HEADER_STRING.getBytes());
        DefaultRequestReader defaultRequestReader = new DefaultRequestReader();
        String readStream = defaultRequestReader.readStream(testInputStream);

        assertThat(readStream).isEqualTo(REQUEST_HEADER_STRING);
    }
}