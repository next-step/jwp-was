package webserver.http;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestTest {

    @DisplayName("Http Request 는 Request Line 문자열 리스트로 만들 수 있다.")
    @Test
    void test() {
        // given
        List<String> requestLines = List.of(
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 59",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*",
                "",
                "userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net"
        );

        // when
        Request request = Request.parseOf(requestLines);

        // then
        RequestLine expectedRequestLine = RequestLine.parseOf("POST /user/create HTTP/1.1");
        Headers expectedHeaders = Headers.parseOf(List.of(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 59",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*"));
        RequestBody expectedBody = new RequestBody("userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net");
        assertThat(request.getRequestLine()).isEqualTo(expectedRequestLine);
        assertThat(request.getHeaders()).isEqualTo(expectedHeaders);
        assertThat(request.getRequestBody()).isEqualTo(expectedBody);
    }
}