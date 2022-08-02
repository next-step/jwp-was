package webserver.request;

import enums.HttpMethod;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {
    @Test
    @DisplayName("HTTP request가 주어졌을 때 알맞게 파싱할 수 있다.")
    void parseTest() {
        List<String> request = List.of(("GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Cookie: logined=true\n" +
                "Accept: */*").split("\n"));

        HttpRequest result = new HttpRequest(request);

        assertEquals(result.getMethod(), HttpMethod.GET);
        assertEquals(result.getPath(), "/index.html");
        assertEquals(result.getProtocol(), "HTTP");
        assertEquals(result.getVersion(), "1.1");
        assertEquals(result.getHeaders(), Map.of(
                "Host", "localhost:8080",
                "Connection", "keep-alive",
                "Accept", "*/*",
                "Cookie", "logined=true"
        ));
        assertEquals(result.getCookie("logined").getValue(), "true");
    }
}
