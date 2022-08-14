package http.httprequest.requestheader;

import http.httpresponse.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestHeaderTest {

    @Test
    @DisplayName("null로 생성하면 빈 상태로 생성")
    void empty() {
        assertThat(RequestHeader.from((List<String>) null))
                .isEqualTo(RequestHeader.from(Collections.emptyMap()));
    }

    @Test
    @DisplayName("content length 조회")
    void contentLength() {
        RequestHeader requestHeader = RequestHeader.from(Map.of(HttpHeaders.CONTENT_LENGTH, "75"));

        assertThat(requestHeader.getContentLength())
                .isEqualTo(75);
    }


    @Test
    @DisplayName("content length 기본값 조회")
    void emptyRequestHeaderContentLength() {
        RequestHeader requestHeader = RequestHeader.from((List<String>) null);

        assertThat(requestHeader.getContentLength()).isZero();
    }

    @Test
    @DisplayName("content length 기본값 조회")
    void cookie() {
        RequestHeader requestHeader = RequestHeader.from(Map.of(HttpHeaders.COOKIE, "logined=true"));

        assertThat(requestHeader.getCookie())
                .isEqualTo("logined=true");
    }

    @Test
    @DisplayName("key value값이 정상적으로 들어가는지 확인한다")
    void put() {
        RequestHeader requestHeader = RequestHeader.from(List.of("URL: localhost:8080"));
        RequestHeader expected = RequestHeader.from(Map.of("URL", "localhost:8080"));

        assertThat(requestHeader).isEqualTo(expected);
    }


}