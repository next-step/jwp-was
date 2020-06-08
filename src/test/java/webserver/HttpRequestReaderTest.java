package webserver;

import http.HttpMethod;
import mock.MockSocket;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;


public class HttpRequestReaderTest {

    @Test
    void read() {
        String request = "GET /index.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: */*\r\n" +
                "\r\n";
        MockSocket socket = new MockSocket(request);

        InputStream in = socket.getInputStream();

        HttpRequest httpRequest = HttpRequestReader.read(in);
        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getPath()).isEqualTo("/index.html");
        assertThat(httpRequest.getProtocol()).isEqualTo("HTTP");
        assertThat(httpRequest.getVersion()).isEqualTo("1.1");

        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getHeader("Accept")).isEqualTo("*/*");
    }

    @Test
    void readAndResponse() throws Exception {
        String request = "GET /index.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: */*\r\n" +
                "\r\n";

        MockSocket socket = new MockSocket(request);

        InputStream in = socket.getInputStream();

        HttpRequest httpRequest = HttpRequestReader.read(in);

        byte[] body = RequestMappingManager.fileLoadFromPath(httpRequest.getPath());

        assertThat(body).isEqualTo(FileIoUtils.loadFileFromClasspath("./templates/index.html"));
    }
}
