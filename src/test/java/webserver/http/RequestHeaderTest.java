package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.domain.RequestHeader;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHeaderTest {
    @Test
    @DisplayName("Request Header 정보를 가져온다.")
    void requestHeader() {
        // when
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.addHeader("Host: localhost:8080");

        // then
        String headerValue = requestHeader.getValue("Host");
        assertThat(headerValue).isEqualTo("localhost:8080");
    }
}
