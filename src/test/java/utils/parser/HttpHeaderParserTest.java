package utils.parser;

import model.HttpHeader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HttpHeaderParserTest {
    @Test
    @DisplayName("List 형태의 Header: Value가 입력으로 주어지면 Header 객체를 반환한다.")
    void parseHeaderTest() {
        List<String> input = Arrays.asList("Host: localhost:8080", "Connection: keep-alive", "Accept: */*");

        HttpHeader result = HttpHeaderParser.parseHeader(input);

        assertAll(
            () -> assertThat(result.getClass()).isEqualTo(HttpHeader.class),
            () -> assertThat(result.getValueByKey("Host")).isEqualTo("localhost:8080"),
            () -> assertThat(result.getValueByKey("Connection")).isEqualTo("keep-alive"),
            () -> assertThat(result.getValueByKey("Accept")).isEqualTo("*/*")
        );
    }
}
