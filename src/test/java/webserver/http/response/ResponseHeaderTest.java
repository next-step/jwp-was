package webserver.http.response;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseHeaderTest {

    @Test
    void toPrint() {
        final ResponseHeader responseHeader = new ResponseHeader(Map.of(
                "Content-Type", "text/html;charset=utf-8",
                "Content-Length", "100"
        ));
        final List<String> expected = Arrays.asList("Content-Type: text/html;charset=utf-8\r\n", "Content-Length: 100\r\n");

        assertEquals(expected, responseHeader.toPrint());
    }
}
