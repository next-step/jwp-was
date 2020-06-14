package http.request;

import mock.MockSocket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HttpRequestHeaderTest {

    BufferedReader br;

    @BeforeAll
    public void init() throws Exception {
        String request = "GET /index.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: */*\r\n";
        MockSocket socket = new MockSocket(request);

        InputStream in = socket.getInputStream();
        this.br = HttpRequestReader.readBuffer(in);
    }

    @Test
    public void create() throws Exception {
        Map<String, String> headerMap = HttpRequestReader.readHeader(br);

        HttpRequestHeader header = new HttpRequestHeader(headerMap);

        assertThat(header.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(header.hasBody()).isEqualTo(false);

    }

}
