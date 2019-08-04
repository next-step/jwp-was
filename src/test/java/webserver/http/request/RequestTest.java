package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpMethod;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RequestTest {

    @DisplayName("http request header 마지막 줄이 null일 때 무한루프에 빠지면 안된다.")
    @Test
    void nullcheckt() {
        // given
        // when
        final List<String> requestHeaders = Arrays.asList(
                "GET /index.html HTTP/1.1",
                null);

        Request request = new Request(requestHeaders);

        // then
        assertThat(request.getRequestLine().getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getRequestLine().path()).isEqualTo("/index.html");
        assertThat(request.getRequestLine().getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    void name() {
        // given
        final List<String> requestHeaders = Arrays.asList(
                "GET /index.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");

        Request request = new Request(requestHeaders);

        // when


        // then
        assertThat(request.getRequestLine().getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getRequestLine().path()).isEqualTo("/index.html");
        assertThat(request.getRequestLine().getHttpVersion()).isEqualTo("HTTP/1.1");
    }
}