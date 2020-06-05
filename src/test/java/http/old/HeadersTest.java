package http.old;

import http.request.headers.Headers;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

public class HeadersTest {
    @Test
    void create() throws IOException {
        //given
        String stringRequest = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Reader reader = new StringReader(stringRequest);

        //when
        Headers headers = new Headers(new BufferedReader(reader));

        //then
        assertThat(headers.getHeaders()).hasSize(5);
        assertThat(headers.getHeaders().get("Host")).isEqualTo("localhost:8080");
    }
}