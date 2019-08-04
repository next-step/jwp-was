package webserver;

import org.junit.jupiter.api.Test;
import webserver.http.RequestLine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHandlerTest {
    @Test
    void readHttpRequest() throws IOException {
        //given
        String httpRequestHeader = "GET / HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Cache-Control: max-age=0\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\n" +
                "Cookie: Idea-efa8df2d=1bb5567d-7139-475d-a323-ee4e3492e089; Idea-dcb08711=75f15d7b-5df3-4803-abcc-3cc01d84def2";
        InputStream inputStream = new ByteArrayInputStream(httpRequestHeader.getBytes());

        //when
        RequestHandler requestHandler = new RequestHandler(null);
        RequestLine requestLine = requestHandler.readRequestLine(inputStream);

        //then
        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath().getPath()).isEqualTo("/");
    }
}