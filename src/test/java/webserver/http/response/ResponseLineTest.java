package webserver.http.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseLineTest {

    @Test
    void toPrint() {
        final ResponseLine responseLine = ResponseLine.of200();
        final String expected = "HTTP/1.1 200 OK \r\n";

        assertEquals(expected, responseLine.toPrint());
    }
}
