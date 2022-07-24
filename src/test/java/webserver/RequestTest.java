package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.Header;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RequestTest {

    @DisplayName("Request를 받아 Header와 RequestLine으로 분리하는 테스트")
    @Test
    void getRequest() throws IOException {
        assertThat(new Request(new ArrayList<>(List.of("GET /index.html HTTP/1.1", "Connection: keep-alive", "Cache-Control: max-age=0"))))
                .isEqualTo(new Request(new RequestLine("GET /index.html HTTP/1.1"), new Header(List.of("Connection: keep-alive", "Cache-Control: max-age=0"))));
    }
}