package webserver.http;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestStreamTest {
    private String requestString;
    private String data;

    @BeforeEach
    public void setBufferedReader() {
        data = "userId=javajigi&password=password&name=박재성&email=javajigi%40slipp.net";
        requestString = "POST /user/login HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: " + data.length() + "\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                data;
    }

    @Test
    void parse() throws IOException {
        InputStream inputStream = new ByteArrayInputStream(requestString.getBytes());
        RequestStream requestStream = new RequestStream(inputStream);
        List<String> requestLines = Lists.list(requestString.split("\n"));
        int headerLineLastIndex = 5;

        assertThat(requestStream.requestLine()).isEqualTo(requestLines.get(0));
        assertThat(String.join(", ", requestStream.header())).isEqualTo(String.join(", ", requestLines.subList(1, headerLineLastIndex)));
        assertThat(requestStream.body()).isEqualTo(data);
    }
}
