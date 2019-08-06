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
    public InputStream inputStream;

    @BeforeEach
    public void setBufferedReader() {
        String requestString = "POST /user/login HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 69\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=박재성&email=javajigi%40slipp.net";

        inputStream = new ByteArrayInputStream(requestString.getBytes());
    }

    @Test
    void parse() throws IOException {
        RequestStream requestStream = new RequestStream(inputStream);
        List<String> headerStrings = Lists.newArrayList(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 69",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*");

        assertThat(requestStream.requestLine()).isEqualTo("POST /user/login HTTP/1.1");
        assertThat(String.join(", ", requestStream.header())).isEqualTo(String.join(", ", headerStrings));
        assertThat(requestStream.body(69)).isEqualTo("userId=javajigi&password=password&name=박재성&email=javajigi%40slipp.net");
    }
}
