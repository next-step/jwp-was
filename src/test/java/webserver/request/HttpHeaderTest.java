package webserver.request;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.HttpHeader;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpHeaderTest {
    @Test
    @DisplayName("HTTP Header lines가 주어졌을 때 key, value 로 파싱할 수 있다.")
    void parseTest() {
        List<String> headerLines = Arrays.asList("Host: localhost:8080", "Connection: keep-alive");

        HttpHeader result = new HttpHeader(headerLines);

        assertEquals(result.getHeaders(), Map.of("Host", "localhost:8080", "Connection", "keep-alive"));
    }
}
