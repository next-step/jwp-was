package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpMethod;
import webserver.http.HttpVersion;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {

    @DisplayName("http request header 마지막 줄이 null일 때 무한루프에 빠지면 안된다.")
    @Test
    void http_request_header_마지막_줄이_null일_때_무한루프에_빠지면_안된다() throws IOException {
        // given
        final String requestHeader = String.join("\r\n",
                "GET /index.html HTTP/1.1",
                null);

        // when
        HttpRequest request = new HttpRequest(inputStream(requestHeader));

        // then
        assertThat(request.getRequestLine().getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getRequestLine().path()).isEqualTo("/index.html");
        assertThat(request.getRequestLine().getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);
    }

    @DisplayName("단순 index 페이지 조회")
    @Test
    void 단순_index_페이지_조회() throws IOException {
        // given
        final String requestHeader = String.join("\r\n",
                "GET /index.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");

        // when
        HttpRequest request = new HttpRequest(inputStream(requestHeader));

        // then
        assertThat(request.getRequestLine().getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getRequestLine().path()).isEqualTo("/index.html");
        assertThat(request.getRequestLine().getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);
    }

    @DisplayName("Request Body를 정상 추출하는지 테스트")
    @Test
    void Request_Body를_정상_추출하는지_테스트() throws IOException {
        // given
        final String requestHeader = String.join("\r\n",
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 101",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*",
                "",
                "userId=javajigi&password=password&name=%EC%9E%90%EB%B0%94%EC%A7%80%EA%B8%B0&email=javajigi%40slip.net"
                );

        // when
        HttpRequest request = new HttpRequest(inputStream(requestHeader));

        // then
        assertThat(request.getRequestLine().getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(request.getRequestLine().path()).isEqualTo("/user/create");
        assertThat(request.getRequestLine().getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);
        assertThat(request.getMessageBody().getMessageBody()).isEqualTo("userId=javajigi&password=password&name=%EC%9E%90%EB%B0%94%EC%A7%80%EA%B8%B0&email=javajigi%40slip.net");
    }

    private InputStream inputStream(final String requestHeader) {
        return new ByteArrayInputStream(requestHeader.getBytes());
    }
}