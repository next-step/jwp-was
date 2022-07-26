package webserver.http.request.parser;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.Headers;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HeadersParserTest {
    private final HeadersParser headersParser = new HeadersParser(new KeyValuePairParser());

    @DisplayName("Header 문자열이 {key}: {value} 형식으로 이뤄지지 않은 경우, 예외 발생")
    @Test
    void parse_fail() {
        List<String> messages = Lists.list(
                "Host: localhost:8080",
                "Connection"
        );

        assertThatThrownBy(() -> headersParser.parse(messages))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("'key=value' 방식의 값이 아닙니다.");
    }

    @DisplayName("Header 문자열이 {key}: {value} 형식으로 이뤄진 경우, Headers 객체 생성")
    @Test
    void parse() {
        List<String> messages = Lists.list(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36",
                "Content-Length: 23"
        );

        Headers actual = headersParser.parse(messages);
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(fixtureHeader());
    }

    private Headers fixtureHeader() {
        HashMap<String, String> keyValues = new HashMap<>();
        keyValues.put("Host", "localhost:8080");
        keyValues.put("Connection", "keep-alive");
        keyValues.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
        keyValues.put("Content-Length", "23");
        return new Headers(keyValues);
    }
}