package webserver;

import http.RequestLine;
import http.RequestMessage;
import org.junit.jupiter.api.Test;


import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageFactoryTest {

    private static final String newLine = "\n";

    @Test
    void test_makeRequestMessage() throws IOException {
        // given
        StringBuilder input = new StringBuilder();
        input.append("GET /index.html HTTP/1.1").append(newLine)
                .append("Host: localhost:8080").append(newLine)
                .append("Connection: keep-alive").append(newLine)
                .append("Accept: */*").append(newLine);
        InputStream in = new ByteArrayInputStream(input.toString().getBytes());
        // when
        RequestMessage requestMessage = HttpMessageFactory.makeRequestMessage(new BufferedReader(new InputStreamReader(in)));
        // then
        assertThat(requestMessage.getRequestLine().equals(RequestLine.from("GET /index.html HTTP/1.1")));
    }
}
