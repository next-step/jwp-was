package webserver.http;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {

    @Test
    void crate() {
        String headerString = "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "\n";
        InputStream inputStream = new ByteArrayInputStream(headerString.getBytes(Charset.forName("UTF-8")));
        HttpRequest httpRequest = new HttpRequest(inputStream);

        assertThat(httpRequest).isNotNull();
    }
}

