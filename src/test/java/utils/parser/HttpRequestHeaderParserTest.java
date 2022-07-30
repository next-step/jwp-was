package utils.parser;

import model.HttpRequestHeader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static dummy.HttpRequestHeaderDummy.HTTP_REQUEST_HEADER_STRING_DUMMY;
import static enums.HttpMethod.GET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HttpRequestHeaderParserTest {
    @Test
    @DisplayName("InputSteam이 입력으로 주어지면 파싱이 완료된 HttpRequestHeader 객체를 반환한다.")
    void parseHttpRequestHeaderTest() throws IOException {
        String httpRequestHeaderStringDummy = HTTP_REQUEST_HEADER_STRING_DUMMY;

        InputStream inputStream = new ByteArrayInputStream(httpRequestHeaderStringDummy.getBytes());

        HttpRequestHeader result = HttpRequestHeaderParser.parseHttpRequestHeaderParser(inputStream);

        assertAll(
            () -> assertThat(result.getClass()).isEqualTo(HttpRequestHeader.class),
            () -> assertThat(result.getHttpHeader().getValueByKey("Host")).isEqualTo("localhost:8080"),
            () -> assertThat(result.getRequestLine().getHttpMethod()).isEqualTo(GET),
            () -> assertThat(result.getPath()).isEqualTo("/jason/test/"),
            () -> assertThat(result.getRequestLine().getWebProtocol().getType()).isEqualTo("HTTP")
        );
    }
}
