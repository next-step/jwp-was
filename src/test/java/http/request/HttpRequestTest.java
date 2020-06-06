package http.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class HttpRequestTest {

    @Test
    void create() throws IOException {
        String line = "GET /index.html HTTP/1.1";
        InputStream in = new ByteArrayInputStream(line.getBytes());

        HttpRequest httpRequest = HttpRequest.of(line, Arrays.asList(), null);

        assertThat(httpRequest).isEqualTo(HttpRequest.from(in));
    }

    @Test
    void create_Body() throws IOException {
        String line = "POST /user/create HTTP/1.1";
        String header = "Content-Length: 59";
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        String raw = line+"\n" +header +"\n\n"+ body;
        InputStream in = new ByteArrayInputStream(raw.getBytes());

        HttpRequest httpRequest = HttpRequest.from(in);

        assertThat(httpRequest).isEqualTo(HttpRequest.of(line, Arrays.asList(header), body));
    }

    @Test
    void get_parameter() throws IOException {
        String line = "GET /user/create?userId=javajigi HTTP/1.1";
        String header = "Content-Length: 0";

        String raw = line+"\n" +header;
        InputStream in = new ByteArrayInputStream(raw.getBytes());

        HttpRequest httpRequest = HttpRequest.from(in);

        assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi");
    }

    @Test
    void post_parameter() throws IOException {
        InputStream in = getInputStream();

        HttpRequest httpRequest = HttpRequest.from(in);

        assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi");
    }

    private InputStream getInputStream() {
        String line = "POST /user/create HTTP/1.1";
        String header = "Content-Length: 91\nContent-Type: application/x-www-form-urlencoded";
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        String raw = line+"\n" +header +"\n\n"+ body;
        return new ByteArrayInputStream(raw.getBytes());
    }


}
