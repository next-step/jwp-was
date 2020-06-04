package webserver.reader;

import http.HttpMethod;
import http.HttpRequest;
import http.Statics;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("inputstream 으로 부터 읽어낸 http 요청을 http request 객체로 파싱")
class DefaultHttpRequestReaderTest {
    private static final DefaultHttpRequestReader defaultHttpRequestReader = new DefaultHttpRequestReader();


    @Test
    @DisplayName("input stream 을 받아 http request 객체로 파싱")
    void parse() throws IOException {
        InputStream testInputStream = new ByteArrayInputStream(Statics.RAW_REQUEST_STR.getBytes());
        HttpRequest httpRequest = defaultHttpRequestReader.read(testInputStream);

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getPath()).isEqualTo("/foo/bar");
        assertThat(httpRequest.getParameter("zoo")).isEqualTo("xoo");
        assertThat(httpRequest.getProtocol()).isEqualTo("HTTP");
        assertThat(httpRequest.getVersion()).isEqualTo("1.1");
    }
}