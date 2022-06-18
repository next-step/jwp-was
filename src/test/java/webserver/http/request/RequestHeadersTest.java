package webserver.http.request;

import org.junit.jupiter.api.Test;
import webserver.http.Cookie;
import webserver.http.request.RequestHeaders;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHeadersTest {
    @Test
    void HTTP_Request_Header_값을_사용해_RequestHeaders_객체를_생성한다() {
        // when
        final RequestHeaders requestHeaders = RequestHeaders.builder()
                .add("Host: localhost:8080")
                .add("Connection: keep-alive")
                .add("Content-Length: 59")
                .add("Content-Type: application/x-www-form-urlencoded")
                .add("Accept: */*")
                .add("Cookie: a=1; b=2;")
                .build();

        // then
        assertThat(requestHeaders.getHost()).isEqualTo("localhost:8080");
        assertThat(requestHeaders.getConnection()).isEqualTo("keep-alive");
        assertThat(requestHeaders.getContentLength()).isEqualTo(59);
        assertThat(requestHeaders.getContentType()).isEqualTo("application/x-www-form-urlencoded");
        assertThat(requestHeaders.getAccept()).isEqualTo("*/*");
        assertThat(requestHeaders.getCookie("a")).isEqualTo(new Cookie("a", "1"));
        assertThat(requestHeaders.getCookie("b")).isEqualTo(new Cookie("b", "2"));
    }
}
