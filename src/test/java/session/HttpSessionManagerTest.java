package session;

import http.request.HttpRequest;
import http.request.HttpRequestReader;
import mock.MockSocket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HttpSessionManagerTest {

    String SESSIONID;

    @BeforeAll
    public void initGet() throws Exception {
        String uuid = HttpSessionManager.createSession();

        this.SESSIONID = uuid;
    }

    @Test
    void createSession() {
        String uuid = HttpSessionManager.createSession();

        assertThat(uuid.length()).isEqualTo(36);
    }

    @Test
    void cookie() {
        String uuid = HttpSessionManager.createSession();

        String header = HttpSessionManager.getCookieHeader(uuid);

        assertThat(header).isEqualTo("SESSIONID=" + uuid + "; Path=/");
    }

    @Test
    void initSessionInRequest() {
        String request = "GET /index.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Cookie: SESSIONID=" + SESSIONID + "\n" +
                "Accept: */*\r\n" +
                "\r\n";
        MockSocket socket = new MockSocket(request);
        InputStream in = socket.getInputStream();

        HttpRequest httpRequest = HttpRequestReader.read(in);
        httpRequest.getSessionAttribute("aaaa");
    }
}
