package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpHeader;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHeaderTest {

    @DisplayName("request header 한 줄의 이름과 값 파싱")
    @Test
    void of() {

        // given
        String requestHeaderText = "Host: localhost:8080";

        // when
        RequestHeader requestHeader = RequestHeader.of(requestHeaderText);

        // then
        assertThat(requestHeader)
                .isEqualTo(new RequestHeader(HttpHeader.HOST, "localhost:8080"));
    }
}