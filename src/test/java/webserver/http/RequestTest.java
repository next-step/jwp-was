package webserver.http;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestTest {

    @DisplayName("Http Request 는 Request Line, Header, Body 로 이루어진다.")
    @Test
    void createTest() {
        // given
        RequestLine requestLine = RequestLine.parseOf("POST /user/create HTTP/1.1");

        Headers headers = Headers.parseOf(List.of(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 59",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*"));

        String body = "userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net";

        // when
        Request request = new Request(requestLine, headers, body);

        // then

        assertThat(request.getRequestLine()).isEqualTo(requestLine);
        assertThat(request.getHeaders()).isEqualTo(headers);
        assertThat(request.getRequestBody()).isEqualTo(body);
    }
}