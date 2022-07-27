package webserver.header.request;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestHeaderTest {

    @DisplayName("requestLine 데이터를 가져올 수 있다.")
    @Test
    void requestLineTest() {
        RequestHeader requestHeader = RequestHeader.create("GET /docs/index.html HTTP/1.1");

        assertThat(requestHeader.protocolVersion()).isEqualTo("HTTP/1.1");
    }
}