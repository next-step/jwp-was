package utils.parser;

import model.request.HttpRequestMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Map;

import static dummy.HttpRequestHeaderDummy.HTTP_POST_REQUEST_HEADER_STRING_DUMMY;
import static dummy.HttpRequestHeaderDummy.HTTP_REQUEST_HEADER_STRING_DUMMY;
import static enums.HttpMethod.GET;
import static enums.HttpMethod.POST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HttpRequestMessageParserTest {
    @Test
    @DisplayName("InputSteam이 입력으로 주어지면 파싱이 완료된 HttpRequestHeader 객체를 반환한다.")
    void parseHttpRequestHeaderTest() throws IOException {
        String httpRequestHeaderStringDummy = HTTP_REQUEST_HEADER_STRING_DUMMY;

        InputStream inputStream = new ByteArrayInputStream(httpRequestHeaderStringDummy.getBytes());

        HttpRequestMessage result = HttpRequestHeaderParser.parseHttpRequestHeaderParser(inputStream);

        assertAll(
            () -> assertThat(result.getClass()).isEqualTo(HttpRequestMessage.class),
            () -> assertThat(result.getHttpHeader().getValueByKey("Host")).isEqualTo("localhost:8080"),
            () -> assertThat(result.getRequestLine().getHttpMethod()).isEqualTo(GET),
            () -> assertThat(result.getPath()).isEqualTo("/jason/test/"),
            () -> assertThat(result.getRequestLine().getWebProtocol().getType()).isEqualTo("HTTP")
        );
    }

    @Test
    @DisplayName("POST 형식의 InputSteam이 입력으로 주어지면 파싱이 완료된 HttpRequestHeader 객체를 반환한다.")
    void parsePostHttpRequestHeaderTest() throws IOException {
        String httpPostRequestHeaderStringDummy = HTTP_POST_REQUEST_HEADER_STRING_DUMMY;
        Map<String, String> filedNameToValue = Map.of(
            "userId", "javajigi",
            "password", "password",
            "name", "박재성",
            "email", "javajigi@slipp.net"
        );

        InputStream inputStream = new ByteArrayInputStream(httpPostRequestHeaderStringDummy.getBytes());

        HttpRequestMessage result = HttpRequestHeaderParser.parseHttpRequestHeaderParser(inputStream);

        assertAll(
            () -> assertThat(result.getClass()).isEqualTo(HttpRequestMessage.class),
            () -> assertThat(result.getHttpHeader().getValueByKey("Host")).isEqualTo("localhost:8080"),
            () -> assertThat(result.getRequestLine().getHttpMethod()).isEqualTo(POST),
            () -> assertThat(result.getPath()).isEqualTo("/user/create"),
            () -> assertThat(result.getRequestLine().getWebProtocol().getType()).isEqualTo("HTTP"),
            () -> assertThat(result.getRequestBody()).isEqualTo(filedNameToValue)
        );
    }
}
