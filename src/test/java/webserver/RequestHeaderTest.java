package webserver;

import org.junit.jupiter.api.Test;
import webserver.http.RequestHeader;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHeaderTest {

    @Test
    void 요청_Header를_저장한다() {
        String contentTypeKey = "Content-Type";
        String contentTypeValue = "application/x-www-form-urlencoded";
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.addHeader(contentTypeKey, contentTypeValue);

        assertThat(requestHeader.getHeader(contentTypeKey)).isEqualTo(contentTypeValue);
    }
}